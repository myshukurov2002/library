package com.company.library_project.entity;

import com.company.library_project.entity.base.StringBaseEntity;
import com.company.library_project.entity.profile.ProfileEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "closet")
public class ClosetEntity  extends StringBaseEntity {

    @OneToMany(mappedBy = "closetEntity", fetch = FetchType.EAGER)
    private List<WardrobeEntity> wardrobeEntities;

    @ManyToOne
    @JoinColumn(name = "floor_id", insertable = false, updatable = false)
    private FloorEntity floorEntity;

    @Column(name = "floor_id")
    private String floorId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}