package caca.extraction.core.hunting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AnchorNotExistException extends Throwable {
    private String anchorName;
}
