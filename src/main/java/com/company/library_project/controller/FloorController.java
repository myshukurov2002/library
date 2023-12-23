//package com.company.library_project.controller;
//
//import com.company.library_project.dto.ApiResponse;
//import com.company.library_project.service.FloorService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/floor")
//public class FloorController {
//    @Autowired
//    private FloorService floorService;
//
//    @PostMapping("/close/create")
//    @Operation(summary = "create floor ➕", description = "this api used for floor creation")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody FloorDTO dto) {
//        ApiResponse<?> response = floorService.create(dto);
//        if (response.getStatus()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @PutMapping("/close/update/{id}")
//    @Operation(summary = "update floor 🛠️", description = "this api used for floor update")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<?>> update(@PathVariable String id,
//                                                 @Valid @RequestBody FloorDTO dto) {
//        ApiResponse<?> response = floorService.update(id, dto);
//        if (response.getStatus()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @DeleteMapping("/close/delete/{id}")
//    @Operation(summary = "delete floor ❌", description = "this api used for floor delete")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<?>> delete(@PathVariable String id) {
//        ApiResponse<?> response = floorService.delete(id);
//        if (response.getStatus()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @GetMapping("/close/getById/{id}")
//    @Operation(summary = "getById floor 🏣", description = "this api used for floor getById")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<ApiResponse<?>> getById(@PathVariable String id) {
//        ApiResponse<?> response = floorService.getById(id);
//        if (response.getStatus()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
//
//    @GetMapping("/close/getList")
//    @Operation(summary = "getList floor 📄🏣", description = "this api used for floor getList")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<List<?>> getList() {
//        return ResponseEntity.ok(floorService.getList());
//    }
//
//    @GetMapping("/close/paging")
//    @Operation(summary = "paging floor 📖🏣", description = "this api used for floor paging")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<Page<?>> paging(@RequestParam(defaultValue = "0") int page,
//                                          @RequestParam(defaultValue = "30") int size) {
//        return ResponseEntity.ok(floorService.paging(page, size));
//    }
//
//    @GetMapping("/close/paging/by-region/{region}")
//    @Operation(summary = "paging By Region floor 🪧🏣", description = "this api used for floor paging By Region")
//    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
//    public ResponseEntity<Page<?>> pagingByRegion(@PathVariable String region,
//                                                  @RequestParam(defaultValue = "0") int page,
//                                                  @RequestParam(defaultValue = "30") int size) {
//        return ResponseEntity.ok(floorService.pagingByRegion(region, page, size));
//    }
//}
