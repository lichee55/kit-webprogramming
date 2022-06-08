package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Screening extends BaseTimeEntity {
    @Id
    @Column(name = "screening_id")
    private Long id;

    @Column(name = "screening_date", nullable = false)
    private LocalDate screeningDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "screening_price", nullable = false, precision = 5, scale = 1)
    private BigDecimal screeningPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private BigDecimal discountRate;

    @Column(name = "seat_status", nullable = false)
    private String seatStatus; // 1,0,1,1,0,0,0,0,0,0,0,1,1,1,1,0,0, ... (count = theater의 row * col)

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;


}
