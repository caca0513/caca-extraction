package caca.extraction.core.statements;

import caca.extraction.core.models.Area;

public abstract class Statement {
    public abstract boolean match(Area waypoint);
}
