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
@Table(name = "wardrobe")
public class WardrobeEntity extends StringBaseEntity {

    @OneToMany(mappedBy = "wardrobeEntity", fetch = FetchType.EAGER)
    private List<BookEntity> bookEntities;

    @ManyToOne
    @JoinColumn(name = "closet_id", insertable = false, updatable = false)
    private ClosetEntity closetEntity;

    @Column(name = "closet_id")
    private String closetId;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}