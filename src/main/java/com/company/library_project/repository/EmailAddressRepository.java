package com.company.library_project.repository;


import com.company.library_project.entity.EmailAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAddressRepository extends JpaRepository<EmailAddressEntity, Long> {
    EmailAddressEntity getByEmail(String email);
}
