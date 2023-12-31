package com.company.library_project.dto;

import com.company.library_project.entity.FloorEntity;
import com.company.library_project.entity.WardrobeEntity;
import com.company.library_project.entity.profile.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClosetDTO {
    private List<WardrobeEntity> wardrobeEntities;
    private FloorEntity floorEntity;
    private String floorId;
    private ProfileEntity profileEntity;
    private String ownerId;
}