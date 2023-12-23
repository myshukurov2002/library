package com.company.library_project.dto.auth;

import com.company.library_project.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private String id;
    private String email;
    private String phone;//TODO
    private List<ProfileRole> profileRoles;

    public JwtDTO(String email) {
        this.email = email;
    }

    public JwtDTO(List<ProfileRole> profileRoles) {
        this.profileRoles = profileRoles;
    }

    public JwtDTO(String id, List<ProfileRole> profileRoles) {
        this.id = id;
        this.profileRoles = profileRoles;
    }

}
