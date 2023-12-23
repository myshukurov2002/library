//package com.company.library_project.config;
//
//import com.company.service.AttachService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FileAutoCleanupConfig {
//
//    @Autowired
//    private AttachService fileService;
//
//    // Run the task every day at midnight
//    @Scheduled(cron = "0 0 0 * * *")
//    public void cleanupExpiredFilesAndImages() {
//        fileService.deleteExpiredFilesAndImages();
//    }
//
////    @Scheduled(cron = "0 0 0 * * *")
////    public void cleanupExpiredFiles() {
////        fileService.deleteExpiredFiles();
////    }
//}
