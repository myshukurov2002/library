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
@Table(name = "floor")
public class FloorEntity extends StringBaseEntity {

    @Column(name = "floor_number", unique = true)
    private Integer floorNumber = 1;

    @OneToMany(mappedBy = "floorEntity", fetch = FetchType.EAGER)
    private List<ClosetEntity> closetEntities;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
