package caca.extraction.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@SuperBuilder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Area {
    @Column(name = "L")
    private final double left;
    @Column(name = "T")
    private final double top;
    @Column(name = "B")
    private final double right;
    @Column(name = "R")
    private final double bottom;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TREASUREMAP_ID", nullable = false)
    @JsonIgnore
    private TreasureMap map;

    public double size() {
        return Math.abs((right - left) * (bottom - top)) * ((right < left || bottom < top) ? -1 : 1);
    }
}

