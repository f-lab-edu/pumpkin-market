package com.pumpkinmarket.domain.location.town;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "towns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, name = "town_name")
    private String townName;

    @Column(name = "coordinates", nullable = false)
    private MultiPolygon coordinates;
}
