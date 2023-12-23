package com.company.library_project.entity;

import com.company.library_project.entity.base.StringBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "closet")
public class ClosetEntity  extends StringBaseEntity {

    @Column(name = "wardrobe_count")
    private int wardrobeCount = 0;

    @OneToMany(mappedBy = "closetEntity")
    private List<WardrobeEntity> wardrobeEntities;

    @ManyToOne
    @JoinColumn(name = "floor_id", insertable = false, updatable = false)
    private FloorEntity floorEntity;

    @Column(name = "floor_id")
    private String floorId;

    public boolean increaseClosetCount() {
        if (wardrobeCount + 1 <= 10) {
            wardrobeCount++;
            return true;
        }
        return false;
    }

    public boolean decreaseClosetCount() {
        if (wardrobeCount - 1 >= 0) {
            wardrobeCount--;
            return true;
        }
        return false;
    }
}