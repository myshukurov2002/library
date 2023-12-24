package com.company.library_project.config.security;

import com.company.library_project.repository.BookRepository;
import com.company.library_project.repository.TakenBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BookingAutoExpired {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TakenBookRepository takenBookRepository;
    // Run the task every day at midnight for BOOK STATUS = BOOKING
    @Scheduled(cron = "0 0 0 * * *")
    public void bookingAutoExpired() {
        fileService.deleteExpiredFilesAndImages();
    }
}
