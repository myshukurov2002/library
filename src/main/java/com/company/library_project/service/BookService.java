package com.company.library_project.service;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.BookDTO;
import com.company.library_project.entity.BookEntity;
import com.company.library_project.entity.WardrobeEntity;
import com.company.library_project.exp.ItemNotFoundException;
import com.company.library_project.re.BookRepository;
import com.company.library_project.re.WardrobeRepository;
import com.company.library_project.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
}
