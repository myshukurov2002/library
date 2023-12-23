package com.company.library_project.repository;

import com.company.library_project.entity.ProfileRoleEntity;
import com.company.library_project.enums.ProfileRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, String> {
    List<ProfileRoleEntity> findAllByProfileId(String id);

    @Query(value = " select p.profileRole from ProfileRoleEntity as p where p.profileId =:profileId")
    List<ProfileRole> findAllRoleList(@Param("profileId") String profileId);
}
