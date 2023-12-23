package com.company.library_project.service;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.entity.profile.EmailHistoryEntity;
import com.company.library_project.repository.EmailAddressRepository;
import com.company.library_project.repository.EmailHistoryRepository;
import com.company.library_project.util.HTMLUtil;
import com.company.library_project.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailAddressRepository emailAddressRepository;
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Value("${server.url}")
    private String serverUrl;
    @Value("${spring.mail.username}")
    private String fromEmail;

    void sendMimeEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            msg.setFrom(fromEmail);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<?> sendEmailVerification(String toAccount) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        String jwt = JWTUtil.encodeEmailJwt(toAccount);
//        serverUrl = "http://localhost:8082"; // for email port
        String url = serverUrl + "/api/v1/auth/verification/email/" + jwt;

        executor.submit(() -> {
            sendMimeEmail(toAccount, "Library VERIFICATION", HTMLUtil.getRegistrationButton(url));

            EmailHistoryEntity entity = new EmailHistoryEntity();
            entity.setEmail(toAccount);
            entity.setMessage(url);
            emailHistoryRepository.save(entity);
            executor.shutdown();
        });
        return new ApiResponse<>(true, "SUCCESS SEND VERIFICATION PAGE!");
    }
}
