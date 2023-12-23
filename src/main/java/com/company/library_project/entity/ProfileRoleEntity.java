package com.company.library_project.entity;

import com.company.library_project.entity.base.StringBaseEntity;
import com.company.library_project.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "profile_role")
public class ProfileRoleEntity extends StringBaseEntity {

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProfileRole profileRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "profile_id")
    private String profileId;

}
