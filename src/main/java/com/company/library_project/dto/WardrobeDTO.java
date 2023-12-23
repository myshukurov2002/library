package com.company.library_project.dto;


import com.company.library_project.entity.BookEntity;
import com.company.library_project.entity.ClosetEntity;
import com.company.library_project.entity.profile.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WardrobeDTO {// polka
    private List<BookEntity> bookEntities;
    private ClosetEntity closetEntity;
    private String closetId;
    private ProfileEntity profileEntity;
    private String ownerId;
}