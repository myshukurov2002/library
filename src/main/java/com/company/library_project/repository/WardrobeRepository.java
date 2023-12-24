package com.company.library_project.re;

import com.company.library_project.entity.WardrobeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardrobeRepository extends JpaRepository<WardrobeEntity, String> {
    Page<WardrobeEntity> findAllByClosetId(String closetId, Pageable pageable);
}
