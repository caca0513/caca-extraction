package caca.extraction.core.hunting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class IndicationNotValidException extends Exception {
    private final String key;
}
