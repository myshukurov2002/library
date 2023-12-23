package com.company.library_project.controller;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.WardrobeDTO;
import com.company.library_project.service.WardrobeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wardrobe")
public class WardrobeController {
    @Autowired
    private WardrobeService wardrobeService;

    @PostMapping("/create")
    @Operation(summary = "create wardrobe ➕", description = "this api used for wardrobe creation")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody WardrobeDTO dto) {
        ApiResponse<?> response = wardrobeService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update wardrobe 🛠️", description = "this api used for wardrobe update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id,
                                                 @Valid @RequestBody WardrobeDTO dto) {
        ApiResponse<?> response = wardrobeService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete wardrobe ❌", description = "this api used for wardrobe delete")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
        ApiResponse<?> response = wardrobeService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "getById wardrobe 🪜", description = "this api used for wardrobe getById")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
        ApiResponse<?> response = wardrobeService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getList")
    @Operation(summary = "getList wardrobe 📄🪜", description = "this api used for wardrobe getList")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(wardrobeService.getList());
    }

    @GetMapping("/paging")
    @Operation(summary = "paging wardrobe 📖🪜", description = "this api used for wardrobe paging")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(wardrobeService.paging(page, size));
    }

    @GetMapping("/paging/by-closetId/{closetId}")
    @Operation(summary = "paging By Region wardrobe 🪧🪜", description = "this api used for wardrobe paging By Region")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<?>> pagingByClosetId(@PathVariable String closetId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(wardrobeService.pagingByClosetId(closetId, page, size));
    }
}
