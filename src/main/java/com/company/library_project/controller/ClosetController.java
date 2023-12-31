package com.company.library_project.controller;


import com.company.library_project.dto.ApiResponse;
import com.company.library_project.dto.ClosetDTO;
import com.company.library_project.service.ClosetService;
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
@RequestMapping("/api/v1/closet")
public class ClosetController {
    @Autowired
    private ClosetService closetService;

    @PostMapping("/create")
    @Operation(summary = "create closet ➕", description = "this api used for closet creation")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@RequestBody ClosetDTO dto) {
        ApiResponse<?> response = closetService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update closet 🛠️", description = "this api used for closet update")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id,
                                                 @Valid @RequestBody ClosetDTO dto) {
        ApiResponse<?> response = closetService.update(id, dto);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete closet ❌", description = "this api used for closet delete")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
        ApiResponse<?> response = closetService.delete(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "getById closet 📂", description = "this api used for closet getById")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
        ApiResponse<?> response = closetService.getById(id);
        if (response.getStatus()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getList")
    @Operation(summary = "getList closet 📄📂", description = "this api used for closet getList")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(closetService.getList());
    }

    @GetMapping("/paging")
    @Operation(summary = "paging closet 📖📂", description = "this api used for closet paging")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(closetService.paging(page, size));
    }

    @GetMapping("/paging/by-floorId/{floorId}")
    @Operation(summary = "paging By Region closet 🪧📂", description = "this api used for closet paging By Region")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<?>> pagingByFloorId(@PathVariable String floorId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(closetService.pagingByFloorId(floorId, page, size));
    }
}
