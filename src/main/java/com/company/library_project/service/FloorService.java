package com.company.library_project.service;

import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.FloorDTO;
import com.company.library_project.entity.FloorEntity;
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
public class FloorService {
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public FloorEntity toEntity(FloorDTO floorDTO) {
        FloorEntity floorEntity = new FloorEntity();
        floorEntity.setFloorNumber(floorDTO.getFloorNumber());
        return floorEntity;
    }

    public FloorDTO toDTO(FloorEntity floorEntity) {
        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setFloorNumber(floorEntity.getFloorNumber());
        return floorDTO;
    }

    public ApiResponse<?> create(FloorDTO dto) {
        FloorEntity entity = toEntity(dto);
        entity.setOwnerId(SecurityUtil.getCurrentProfileId());

        log.info("floor created " + entity.getId());

        FloorEntity saved = floorRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.created", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> update(String id, FloorDTO dto) {
        Optional<FloorEntity> optionalFloor = floorRepository.findById(id);
        if (optionalFloor.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        FloorEntity entity = optionalFloor.get();
        entity.setFloorNumber(dto.getFloorNumber());

        log.warn("floor updated " + id);

        FloorEntity saved = floorRepository.save(entity);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.updated", SecurityUtil.getProfileLanguage()), toDTO(saved));
    }

    public ApiResponse<?> delete(String id) {
        if (!floorRepository.existsById(id)) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        floorRepository.deleteById(id);
        log.warn("floor deleted " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.deleted", SecurityUtil.getProfileLanguage()));
    }


    public ApiResponse<?> getById(String id) {
        Optional<FloorEntity> optionalFloor = floorRepository.findById(id);
        if (optionalFloor.isEmpty()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", SecurityUtil.getProfileLanguage()));
        }
        FloorEntity entity = optionalFloor.get();
        return new ApiResponse<>(true, toDTO(entity));
    }

    public List<FloorDTO> getList() {
        List<FloorEntity> all = floorRepository.findAll();
        return all
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Page<FloorDTO> paging(int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FloorEntity> entities = floorRepository.findAll(pageable);
        List<FloorDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    public Page<?> pagingByFloorNumber(Integer floorNumber, int page, int size) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FloorEntity> entities = floorRepository.findAllByFloorNumber(floorNumber,pageable);
        List<FloorDTO> dtos = entities
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }
}