package com.company.library_project.controller;


import com.company.library_project.dto.ProfileDTO;
import com.company.library_project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/admin/delete/by-date/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        String response = profileService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<Page<ProfileDTO>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "5") Integer size) {
        Page<ProfileDTO> profileDTOPage = profileService.getAll(page, size);
        return ResponseEntity.ok(profileDTOPage);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin/by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        ProfileDTO profileDTO = profileService.getById(id);
        return ResponseEntity.ok(profileDTO);
    }
}