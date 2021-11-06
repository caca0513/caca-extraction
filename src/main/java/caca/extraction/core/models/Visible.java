package caca.extraction.core.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@SuperBuilder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)
@Entity
public class Visible extends Area {
    private final String content;
}

