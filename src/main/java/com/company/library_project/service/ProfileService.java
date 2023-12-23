package com.company.library_project.service;


import com.company.library_project.dto.ProfileDTO;
import com.company.library_project.entity.profile.ProfileEntity;
import com.company.library_project.exp.ItemNotFoundException;
import com.company.library_project.repository.ProfileRepository;
import com.company.library_project.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    private ProfileDTO ENTITY_TO_DTO(ProfileEntity profileEntity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(profileEntity.getFirstName());
        profileDTO.setSecondName(profileEntity.getSecondName());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setPassword(profileEntity.getPassword());
        profileDTO.setStatus(profileEntity.getStatus());
        return profileDTO;
    }

    private ProfileEntity DTO_TO_ENTITY(ProfileDTO profileDTO) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setSecondName(profileDTO.getSecondName());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setPhone(profileDTO.getPhone());
        profileEntity.setPassword(MD5Util.encode(profileDTO.getPassword()));
        profileEntity.setStatus(profileDTO.getStatus());
        return profileEntity;
    }

    public Page<ProfileDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by("createdDate").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfileEntity> profileEntityPage = profileRepository
                .findAll(pageable);
        List<ProfileDTO> profileDTOList = profileEntityPage
                .stream()
                .map(this::ENTITY_TO_DTO)
                .toList();
        return new PageImpl<>(profileDTOList, pageable, profileEntityPage.getTotalElements());
    }

    public String delete(String id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return "SUCCESS";
        }
        return "USER IS NOT EXISTS";
    }

    public ProfileDTO getById(String id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        try {
            ProfileEntity profileEntity = byId.orElseThrow(() -> new ItemNotFoundException("Item not found"));
            return ENTITY_TO_DTO(profileEntity);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
