package com.pumpkinmarket.domain.location.town;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(
        name = "towns",
        indexes = {
                @Index(
                        name="coordinates_index",
                        columnList="coordinates"
                ),
                @Index(
                        name="code_index",
                        columnList="code"
                )
        }
)
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

    @Column(name = "coordinates", columnDefinition = "geometry NOT NULL SRID 4326")
    private MultiPolygon coordinates;
}
