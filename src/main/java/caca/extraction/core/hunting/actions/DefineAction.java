package caca.extraction.core.hunting.actions;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;

import java.util.List;

public class DefineAction extends Action {

    public DefineAction(String variable) {
        super(variable);
    }

    @Override
    public List<Area> act(Area targetArea, TreasureMap map) {
        return List.of(targetArea);
    }
}
