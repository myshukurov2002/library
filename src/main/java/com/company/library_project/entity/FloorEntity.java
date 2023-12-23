package com.company.library_project.entity;

import com.company.library_project.entity.base.StringBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "floor")
public class FloorEntity extends StringBaseEntity {

    @Column(name = "floor_number")
    private Integer floorNumber = 1;

    @Column(name = "closet_count")
    private Integer closetCount = 0;// shkaf

    @OneToMany(mappedBy = "floorEntity")
    private List<ClosetEntity> closetEntities;

    public boolean increaseClosetCount() {
        if (closetCount + 1 <= 20) {
            closetCount++;
            return true;
        }
        return false;
    }

    public boolean decreaseClosetCount() {
        if (closetCount - 1 >= 0) {
            closetCount--;
            return true;
        }
        return false;
    }
}
