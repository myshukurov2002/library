package com.company.library_project.service;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.BookDTO;
import com.company.library_project.entity.BookEntity;
import com.company.library_project.entity.TakenBookEntity;
import com.company.library_project.entity.WardrobeEntity;
import com.company.library_project.enums.BookStatus;
import com.company.library_project.exp.ItemNotFoundException;
import com.company.library_project.repository.BookRepository;
import com.company.library_project.repository.WardrobeRepository;
import com.company.library_project.repository.TakenBookRepository;
import com.company.library_project.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WardrobeRepository wardrobeRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private TakenBookRepository takenBookRepository;

    public BookEntity toEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        Optional<WardrobeEntity> optionalWardrobe = wardrobeRepository.findById(bookDTO.getWardrobeId());

        if (optionalWardrobe.isEmpty()) {
            throw new ItemNotFoundException("wardrobe id " + resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        WardrobeEntity wardrobeEntity = optionalWardrobe.get();
        if (wardrobeEntity.getBookEntities().size() >= 20) {
            throw new RuntimeException("wardrobe id " + resourceBundleService.getMessage("item.already.full", SecurityUtil.getProfileLanguage()));
        }
        bookEntity.setWardrobeId(bookDTO.getWardrobeId());
//        bookEntity.setCategoryId(bookEntity.getCategoryId()); //TODO add
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublishedDate(bookDTO.getPublishedDate());
        bookEntity.setTitle(bookDTO.getTitle());

        return bookEntity;
    }

    public BookDTO toDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setWardrobeId(bookEntity.getWardrobeId());
        bookDTO.setBookStatus(bookEntity.getBookStatus());
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setPublishedDate(bookEntity.getPublishedDate());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setUniqueName(bookEntity.getUniqueName());
//        bookDTO.setCategoryId(bookEntity.getCategoryId());//TODO add
        return bookDTO;
    }

    public ApiResponse<?> create(BookDTO dto) {
        BookEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());
        entity.setAuthor(dto.getAuthor());
        entity.setPublishedDate(dto.getPublishedDate());
        entity.setTitle(dto.getTitle());

        log.info("book created " + entity.getId());

        BookEntity saved = bookRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> update(String id, BookDTO dto) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        BookEntity entity = optionalBook.get();
        entity.setWardrobeId(dto.getWardrobeId());

        log.warn("book updated " + id);

        BookEntity saved = bookRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> delete(String id) {
        if (!bookRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        bookRepository.deleteById(id);
        log.warn("book deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }

    public ApiResponse<?> getById(String id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        BookEntity entity = optionalBook.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    public List<BookDTO> getList() {
        List<BookEntity> all = bookRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<BookDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> entities = bookRepository.findAll(pageable);
        List<BookDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByWardrobeId(String wardrobeId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> entities = bookRepository.findAllByWardrobeId(wardrobeId, pageable);
        List<BookDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByAuthor(String author, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> entities = bookRepository.findAllByAuthorContaining(author, pageable);
        List<BookDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByTitle(String title, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookEntity> entities = bookRepository.findAllByTitleContaining(title, pageable);
        List<BookDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public ApiResponse<?> getByUniqueName(String uniqueName) {
        Optional<BookEntity> optionalBook = bookRepository.findAllByUniqueNameAndVisibilityTrue(uniqueName);
        if (optionalBook.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        BookEntity entity = optionalBook.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    public ApiResponse<?> booking(String uniqueName) {

        BookEntity entity = get(uniqueName);

        if (!entity.getBookStatus().equals(BookStatus.AVAILABLE)) {
            return new ApiResponse<>(false, "Book Unavailable! " + resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        takenBookRepository.deleteAllByBookId(entity.getId());

        TakenBookEntity takenBookEntity = new TakenBookEntity();
        takenBookEntity.setBookId(entity.getId());
        takenBookEntity.setUserId(SecurityUtil.getCurrentProfileId());
        takenBookEntity.setBookingDay(LocalDate.now().plusDays(1));
        takenBookEntity.setStatus(BookStatus.BOOKING);
        takenBookEntity.setNote(entity.getTitle() + " -> " + takenBookEntity.getStatus());

        entity.setBookStatus(BookStatus.BOOKING);
        entity.setBookingDay(LocalDate.now().plusDays(1));
        bookRepository.save(entity);
        takenBookRepository.save(takenBookEntity);

        log.warn("booking  " + entity.getId());

        BookEntity saved = bookRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }


    public ApiResponse<?> takeBook(String uniqueName) {

        BookEntity entity = get(uniqueName);


        List<TakenBookEntity> optionalTakenBook = takenBookRepository
                .findByBookId(entity.getId());
        if (optionalTakenBook.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        TakenBookEntity takenBookEntity = optionalTakenBook.get(0);

        if (!takenBookEntity.getStatus().equals(BookStatus.BOOKING)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }

        if (!takenBookEntity.getUserId().equals(SecurityUtil.getCurrentProfileId())) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("you.are.not.allowed", SecurityUtil.getProfileLanguage()));
        }

        takenBookEntity.setStatus(BookStatus.TAKEN);
        takenBookEntity.setNote(takenBookEntity.getNote() + " -> " + takenBookEntity.getStatus());
        takenBookEntity.setBookingDay(LocalDate.now().plusDays(3));// booking for 3 days

        entity.setBookStatus(BookStatus.TAKEN);
        entity.setBookingDay(LocalDate.now().plusDays(3));
        bookRepository.save(entity);
        takenBookRepository.save(takenBookEntity);

        log.warn("taken  " + entity.getId());

        BookEntity saved = bookRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }


    public ApiResponse<?> returnBook(String uniqueName) {
        BookEntity entity = get(uniqueName);


        List<TakenBookEntity> optionalTakenBook = takenBookRepository
                .findByBookId(entity.getId());
        if (optionalTakenBook.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        TakenBookEntity takenBookEntity = optionalTakenBook.get(0);

        if (!takenBookEntity.getUserId().equals(SecurityUtil.getCurrentProfileId())) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("you.are.not.allowed", SecurityUtil.getProfileLanguage()));
        }

        if (!takenBookEntity.getStatus().equals(BookStatus.TAKEN)) {
            return new ApiResponse<>(false, "This book already returned" +  resourceBundleService.getMessage("you.are.not.allowed", SecurityUtil.getProfileLanguage()));
        }

//        takenBookEntity.setStatus(BookStatus.RETURNED);
//        takenBookEntity.setNote(takenBookEntity.getNote() + " -> " + takenBookEntity.getStatus());
//        takenBookEntity.setBookingDay(null);

        entity.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.save(entity);
        takenBookRepository.delete(takenBookEntity);

        log.warn("returned  " + entity.getId());

        BookEntity saved = bookRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));

    }

    public BookEntity get(String uniqueName) {
        Optional<BookEntity> optionalBook = bookRepository.findAllByUniqueNameAndVisibilityTrue(uniqueName);
        if (optionalBook.isEmpty()) {
            throw new ItemNotFoundException(resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        return optionalBook.get();
    }
}
