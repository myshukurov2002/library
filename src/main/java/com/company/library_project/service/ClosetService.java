package com.company.library_project.service;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.ClosetDTO;
import com.company.library_project.entity.ClosetEntity;
import com.company.library_project.entity.FloorEntity;
import com.company.library_project.exp.ItemNotFoundException;
import com.company.library_project.repository.ClosetRepository;
import com.company.library_project.repository.FloorRepository;
import com.company.library_project.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ClosetService {
    @Autowired
    private ClosetRepository closetRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public ClosetEntity toEntity(ClosetDTO closetDTO) {
        ClosetEntity closetEntity = new ClosetEntity();
        Optional<FloorEntity> optionalFloor = floorRepository.findById(closetDTO.getFloorId());

        if (optionalFloor.isEmpty()) {
            throw new ItemNotFoundException("floor id " + resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        FloorEntity floorEntity = optionalFloor.get();
        if (floorEntity.getClosetEntities().size() >= 20) {
            throw new RuntimeException("floor id " + resourceBundleService.getMessage("item.already.full", SecurityUtil.getProfileLanguage()));
        }
        closetEntity.setFloorId(closetDTO.getFloorId());
        return closetEntity;
    }

    public ClosetDTO toDTO(ClosetEntity closetEntity) {
        ClosetDTO closetDTO = new ClosetDTO();
        closetDTO.setFloorId(closetEntity.getFloorId());
        return closetDTO;
    }

    public ApiResponse<?> create(ClosetDTO dto) {
        ClosetEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("closet created " + entity.getId());

        ClosetEntity saved = closetRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> update(String id, ClosetDTO dto) {
        Optional<ClosetEntity> optionalCloset = closetRepository.findById(id);
        if (optionalCloset.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        ClosetEntity entity = optionalCloset.get();
        entity.setFloorId(dto.getFloorId());

        log.warn("closet updated " + id);

        ClosetEntity saved = closetRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> delete(String id) {
        if (!closetRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        closetRepository.deleteById(id);
        log.warn("closet deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }


    public ApiResponse<?> getById(String id) {
        Optional<ClosetEntity> optionalCloset = closetRepository.findById(id);
        if (optionalCloset.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        ClosetEntity entity = optionalCloset.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    public List<ClosetDTO> getList() {
        List<ClosetEntity> all = closetRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<ClosetDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ClosetEntity> entities = closetRepository.findAll(pageable);
        List<ClosetDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByFloorId(String floorId, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ClosetEntity> entities = closetRepository.findAllByFloorIdAndVisibilityTrue(floorId, pageable);
        List<ClosetDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}