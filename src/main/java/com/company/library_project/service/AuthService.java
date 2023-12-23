package com.company.library_project.service;

import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.ProfileDTO;
import com.company.library_project.dto.auth.AuthDTO;
import com.company.library_project.dto.auth.RegistrationDTO;
import com.company.library_project.entity.profile.ProfileEntity;
import com.company.library_project.entity.profile.ProfileRoleEntity;
import com.company.library_project.enums.Language;
import com.company.library_project.enums.ProfileRole;
import com.company.library_project.enums.ProfileStatus;
import com.company.library_project.repository.EmailHistoryRepository;
import com.company.library_project.repository.ProfileRepository;
import com.company.library_project.repository.ProfileRoleRepository;
import com.company.library_project.util.JWTUtil;
import com.company.library_project.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private MailSenderService mailSenderService;

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setThirdName(entity.getThirdName());
        dto.setEmail(entity.getEmail());
        dto.setLang(entity.getLang());
        dto.setBirthDate(entity.getBirthDate());
        dto.setNationality(entity.getNationality());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());

        List<ProfileRole> roleList = profileRoleRepository.findAllRoleList(entity.getId());

        dto.setRoles(roleList);//TODO remove
        dto.setJwt(JWTUtil.encode(entity.getId(), roleList));
        return dto;
    }

    private ProfileEntity toEntity(RegistrationDTO reg) {
        ProfileEntity entity = new ProfileEntity();
        entity.setFirstName(reg.getFirstName());
        entity.setSecondName(reg.getSecondName());
        entity.setThirdName(reg.getThirdName());
        entity.setEmail(reg.getEmail());
        entity.setBirthDate(reg.getBirthDate());
        entity.setPhone(reg.getPhone());
        entity.setStatus(reg.getStatus());
        entity.setPassword(MD5Util.encode(reg.getPassword()));

        entity.setProfileRoleList(List.of(new ProfileRoleEntity(ProfileRole.USER, entity.getId())));

        return entity;
    }

    public ApiResponse<?> login(AuthDTO dto, Language lang) {
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            ProfileEntity entity = byEmail.get();
            log.info("login " + dto.getEmail());
            return new ApiResponse<>(true, toDTO(entity));
        }
        return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", lang));
    }

    public ApiResponse<?> registration(RegistrationDTO dto, Language lang) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findByEmail(dto.getEmail());
        if (optionalProfile.isPresent()) {
            if (optionalProfile.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optionalProfile.get()); // delete
            } else {
                return new ApiResponse<>(false, resourceBundleService.getMessage("email.already.exists", lang) + "try again");
            }
        }

        Long count = emailHistoryRepository.countAllByEmailAndCreatedDateAfter(dto.getEmail(), LocalDateTime.now().minusMinutes(1));
        if (count > 4) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("try.again.around.1minute", lang));
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);

        log.info("registration  " + dto.getEmail());
        return mailSenderService.sendEmailVerification(dto.getEmail());
    }
}
