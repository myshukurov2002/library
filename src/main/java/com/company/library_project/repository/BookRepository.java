package com.company.library_project.re;

import com.company.library_project.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    Page<BookEntity> findAllByWardrobeId(String wardrobeId, Pageable pageable);
//    @Query(value = "SELECT *" +
//                   " FROM book" +
//                   " WHERE author LIKE '%' || :author || '%'", nativeQuery = true)
    Page<BookEntity> findAllByAuthorContaining(String author, Pageable pageable);

    Page<BookEntity> findAllByTitleContaining(String title, Pageable pageable);

    Optional<BookEntity> findAllByUniqueNameAndVisibilityTrue(String uniqueName);
}
