package caca.extraction.core.hunting.actions;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Action {
    private String variable;

    public abstract List<Area> act(Area targetArea, TreasureMap map);
}


