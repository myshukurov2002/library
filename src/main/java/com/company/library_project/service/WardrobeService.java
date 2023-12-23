package com.company.library_project.service;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.WardrobeDTO;
import com.company.library_project.entity.ClosetEntity;
import com.company.library_project.entity.WardrobeEntity;
import com.company.library_project.exp.ItemNotFoundException;
import com.company.library_project.re.ClosetRepository;
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
public class WardrobeService {
    @Autowired
    private WardrobeRepository wardrobeRepository;
    @Autowired
    private ClosetRepository closetRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public WardrobeEntity toEntity(WardrobeDTO wardrobeDTO) {
        WardrobeEntity wardrobeEntity = new WardrobeEntity();
        Optional<ClosetEntity> optionalCloset = closetRepository.findById(wardrobeDTO.getClosetId());

        if (optionalCloset.isEmpty()) {
            throw new ItemNotFoundException("closet id " + resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        ClosetEntity closetEntity = optionalCloset.get();
        if (closetEntity.getWardrobeEntities().size() >= 10) {
            throw new RuntimeException("closet id " + resourceBundleService.getMessage("item.already.full", SecurityUtil.getProfileLanguage()));
        }
        wardrobeEntity.setClosetId(wardrobeDTO.getClosetId());
        return wardrobeEntity;
    }

    public WardrobeDTO toDTO(WardrobeEntity wardrobeEntity) {
        WardrobeDTO wardrobeDTO = new WardrobeDTO();
        wardrobeDTO.setClosetId(wardrobeEntity.getClosetId());
        return wardrobeDTO;
    }

    public ApiResponse<?> create(WardrobeDTO dto) {
        WardrobeEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("wardrobe created " + entity.getId());

        WardrobeEntity saved = wardrobeRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> update(String id, WardrobeDTO dto) {
        Optional<WardrobeEntity> optionalWardrobe = wardrobeRepository.findById(id);
        if (optionalWardrobe.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        WardrobeEntity entity = optionalWardrobe.get();
        entity.setClosetId(dto.getClosetId());

        log.warn("wardrobe updated " + id);

        WardrobeEntity saved = wardrobeRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> delete(String id) {
        if (!wardrobeRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        wardrobeRepository.deleteById(id);
        log.warn("wardrobe deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }


    public ApiResponse<?> getById(String id) {
        Optional<WardrobeEntity> optionalWardrobe = wardrobeRepository.findById(id);
        if (optionalWardrobe.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        WardrobeEntity entity = optionalWardrobe.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    public List<WardrobeDTO> getList() {
        List<WardrobeEntity> all = wardrobeRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<WardrobeDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<WardrobeEntity> entities = wardrobeRepository.findAll(pageable);
        List<WardrobeDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByClosetId(String closetId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<WardrobeEntity> entities = wardrobeRepository.findAllByClosetId(closetId, pageable);
        List<WardrobeDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}
