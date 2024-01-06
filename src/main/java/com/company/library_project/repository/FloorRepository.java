package com.company.library_project.repository;

import com.company.library_project.entity.FloorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FloorRepository extends JpaRepository<FloorEntity, String> {
    Page<FloorEntity> findAllByFloorNumber(Integer floorNumber, Pageable pageable);
    Optional<FloorEntity> findByFloorNumberAndVisibilityTrue(Integer floorNumber);
}
