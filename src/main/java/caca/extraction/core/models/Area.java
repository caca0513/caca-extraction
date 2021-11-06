package caca.extraction.core.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@SuperBuilder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final double left;
    private final double top;
    private final double right;
    private final double bottom;
}

