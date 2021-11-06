package caca.extraction.core.statements;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.Visible;
import lombok.Data;

@Data
public class HasContentStatement extends Statement {

    private final String content;

    @Override
    public boolean match(Area waypoint) {
        return waypoint instanceof Visible && ((Visible) waypoint).getContent().contains(content);
    }
}
