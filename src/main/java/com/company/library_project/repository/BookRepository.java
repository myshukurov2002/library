package com.company.library_project.repository;

import com.company.library_project.entity.BookEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    Page<BookEntity> findAllByWardrobeId(String wardrobeId, Pageable pageable);
//    @Query(value = "SELECT *" +
//                   " FROM book" +
//                   " WHERE author LIKE '%' || :author || '%'", nativeQuery = true)
    Page<BookEntity> findAllByAuthorContaining(String author, Pageable pageable);

    Page<BookEntity> findAllByTitleContaining(String title, Pageable pageable);

    Optional<BookEntity> findAllByUniqueNameAndVisibilityTrue(String uniqueName);

//    @Transactional
//    @Modifying
//    @Query("update BookEntity  as b" +
//           " set  b.bookStatus='AVAILABE'" +
//           " where b.bookStatus='BOOKING'")
//    void update();

    @Transactional
    @Modifying
    @Query("update BookEntity as b set b.bookStatus = 'AVAILABLE' where b.bookStatus = 'BOOKING' and b.bookingDay < CURRENT_DATE")
    void updateAllByBookingDayIsBeforeNow();

}
