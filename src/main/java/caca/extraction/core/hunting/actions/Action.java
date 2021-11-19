package caca.extraction.core.hunting.actions;

import caca.extraction.core.hunting.Memo;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Action {
    private String variable;

    public abstract void act(Area targetArea, Memo memo, TreasureMap map);
}


