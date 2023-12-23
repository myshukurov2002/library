package com.company.library_project.controller;

import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.auth.AuthDTO;
import com.company.library_project.dto.auth.RegistrationDTO;
import com.company.library_project.enums.Language;
import com.company.library_project.service.AuthService;
import com.company.library_project.service.EmailSenderService;
import com.company.library_project.util.HTMLUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private EmailSenderService emailSenderService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody AuthDTO dto,
                                             @RequestParam(defaultValue = "en") Language lang) {
        return ResponseEntity.ok(authService.login(dto, lang));
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<?>> registration(@RequestBody RegistrationDTO dto,
                                                    @RequestParam(defaultValue = "en") Language lang) {
        return ResponseEntity.ok(authService.registration(dto, lang));
    }
    @GetMapping(value = "/verification/email/{jwt}")
    public void registration(@PathVariable String jwt,
                             HttpServletResponse response,
                             @RequestParam(defaultValue = "en") Language lang) {
        ApiResponse<?> apiResponse = emailSenderService.emailVerification(jwt, lang);

        if (apiResponse.getStatus()) {
            try {
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(HTMLUtil.getResponse());
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ResponseEntity<ApiResponse<?>> error = new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
