package com.company.library_project.dto;

import com.company.library_project.entity.ClosetEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FloorDTO {
    private Integer floorNumber;
    private Integer closetCount;// shkaf
    private List<ClosetEntity> closetEntities;
}
