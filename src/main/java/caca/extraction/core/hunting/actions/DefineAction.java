package caca.extraction.core.hunting.actions;


import caca.extraction.core.hunting.Memo;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;

import java.util.List;

public class DefineAction extends Action {

    public DefineAction(String variable) {
        super(variable);
    }

    @Override
    public void act(Area targetArea, Memo memo, TreasureMap map) {
        if (memo.getAnchors().containsKey(getVariable()))
            memo.getAnchors().get(getVariable()).add(targetArea);
        else
            memo.getAnchors().put(getVariable(), List.of(targetArea));
    }
}
