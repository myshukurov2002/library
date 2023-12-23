package com.company.library_project.entity;


import com.company.library_project.entity.base.StringBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "wardrobe")
public class WardrobeEntity extends StringBaseEntity {

    @Column(name = "book_count")
    private Integer bookCount = 0;// polka

    @OneToMany(mappedBy = "wardrobeEntity")
    private List<BookEntity> bookEntities;

    @ManyToOne
    @JoinColumn(name = "closet_id", insertable = false, updatable = false)
    private ClosetEntity closetEntity;

    @Column(name = "closet_id")
    private String closetId;

    public boolean increaseBookCount() {
        if (bookCount + 1 <= 20) {
            bookCount++;
            return true;
        }
        return false;
    }

    public boolean decreaseBookCount() {
        if (bookCount - 1 >= 0) {
            bookCount--;
            return true;
        }
        return false;
    }
}