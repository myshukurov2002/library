package com.company.library_project.repository;

import com.company.library_project.entity.ClosetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosetRepository extends JpaRepository<ClosetEntity, String> {
    Page<ClosetEntity> findAllByFloorIdAndVisibilityTrue(String floorId, Pageable pageable);
}
