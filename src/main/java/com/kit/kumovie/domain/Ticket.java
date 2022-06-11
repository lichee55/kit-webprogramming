package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tickets")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column(name = "price", nullable = false, precision = 6, scale = 1)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber; // 0,1;0,2;0,3;... (divide by ';')(count = seatCount)

    public void setMember(Member member) {
        this.member = member;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }
}
