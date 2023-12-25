package com.company.library_project.config;

import com.company.library_project.repository.BookRepository;
import com.company.library_project.repository.TakenBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingExpiredScheduled {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TakenBookRepository takenBookRepository;

    // Run the task every hour for BOOK and TakenBOok STATUS = BOOKING and time expring
    @Scheduled(cron = "0 0 * * * *")
    public void bookingAutoExpired() {
        bookRepository.updateAllByBookingDayIsBeforeNow();
        takenBookRepository.updateAllByBookingDayIsBeforeNow();
    }

}
