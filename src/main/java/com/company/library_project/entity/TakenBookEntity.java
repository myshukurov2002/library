package com.company.library_project.entity;import com.company.library_project.entity.base.StringBaseEntity;import com.company.library_project.entity.profile.ProfileEntity;import com.company.library_project.enums.BookStatus;import jakarta.persistence.*;import lombok.Getter;import lombok.Setter;import java.time.LocalDate;@Setter@Getter@Entity@Table(name = "taken_book")public class TakenBookEntity extends StringBaseEntity {    @ManyToOne    @JoinColumn(name = "profile_id", insertable = false, updatable = false)    private ProfileEntity profileEntity;    @Column(name = "profile_id")    private String userId;    @ManyToOne    @JoinColumn(name = "book_id", insertable = false, updatable = false)    private BookEntity bookEntity;    @Column(name = "book_id")    private String bookId;    @Enumerated(EnumType.STRING)    private BookStatus status;    @Column(name = "booking_day")    private LocalDate bookingDay;    @Column(name = "note",            columnDefinition = "TEXT")    private String note;}