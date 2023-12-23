package com.company.library_project.entity;

import com.company.library_project.entity.base.StringBaseEntity;
import com.company.library_project.enums.Language;
import com.company.library_project.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity extends StringBaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "third_name")
    private String thirdName; // father's name

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status = ProfileStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    private Language lang = Language.en;

    @OneToMany(mappedBy = "profileEntity", fetch = FetchType.EAGER)
    private List<ProfileRoleEntity> profileRoleList;

}
