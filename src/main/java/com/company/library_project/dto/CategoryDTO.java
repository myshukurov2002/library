package com.company.library_project.dto;import com.fasterxml.jackson.annotation.JsonInclude;import lombok.*;import java.time.LocalDateTime;@Setter@Getter@JsonInclude(JsonInclude.Include.NON_NULL)public class CategoryDTO {    private Integer id;    private String name;    private LocalDateTime createdDate;    private Boolean visibility;}