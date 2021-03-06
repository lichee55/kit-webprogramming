package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screenings")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Screening extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "screening_price", nullable = false, precision = 6, scale = 1)
    private BigDecimal screeningPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", precision = 6, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "seat_status", nullable = false)
    private String seatStatus; // 1,0,1,1,0,0,0,0,0,0,0,1,1,1,1,0,0, ... (count = theater의 row * col)

    @Column(name = "rest_seat_count", nullable = false)
    private Integer restSeatCount;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screening", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public void setRestSeatCount(Integer restSeatCount) {
        this.restSeatCount = restSeatCount;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }
}
