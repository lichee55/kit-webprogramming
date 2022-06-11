package com.kit.kumovie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "theaters")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theater extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "floor_count", nullable = false)
    private Integer floorCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @Column(name = "row_count", nullable = false)
    private Integer rowCount;

    @Column(name = "col_count", nullable = false)
    private Integer colCount;

}
