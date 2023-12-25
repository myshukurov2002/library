package com.company.library_project.repository;

import com.company.library_project.entity.TakenBookEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TakenBookRepository extends JpaRepository<TakenBookEntity, String> {

//    Optional<TakenBookEntity> findByBookId(String id);
    List<TakenBookEntity> findByBookId(String id);
//    @Transactional
//    @Modifying
//    @Query("update TakenBookEntity  as t" +
//           " set  t.status='RETURNED'" +
//           " where t.status='BOOKING'")
//    void update();

    List<TakenBookEntity> findAllBy();
    @Transactional
    @Modifying
    @Query("update TakenBookEntity as t set t.status = 'AVAILABLE' where t.status = 'BOOKING' and t.bookingDay < CURRENT_DATE")
    void updateAllByBookingDayIsBeforeNow();

    void deleteAllByBookId(String id);
}
