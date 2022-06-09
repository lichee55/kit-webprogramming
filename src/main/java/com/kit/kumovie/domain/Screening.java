package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "screening_price", nullable = false, precision = 5, scale = 1)
    private BigDecimal screeningPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", precision = 5, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "seat_status", nullable = false)
    private String seatStatus; // 1,0,1,1,0,0,0,0,0,0,0,1,1,1,1,0,0, ... (count = theater의 row * col)

    @Column(name = "rest_seat_count", nullable = false)
    private Integer restSeatCount;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;


}
