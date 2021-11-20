package caca.extraction.core.hunting.actions;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;

import java.util.List;
import java.util.stream.Collectors;

public class FindAction extends Action {

    public FindAction(String variable) {
        super(variable);
    }

    @Override
    public List<Area> act(Area targetArea, TreasureMap map) {
        //remove the surrounding { and }
        var key = getVariable().substring(1, getVariable().length()-1);
        var anchors = map.getWaypoints().stream()
                .filter(Visible.class::isInstance)
                .filter(wp -> Areas.isIntersect(wp, targetArea))
                .map(Visible.class::cast)
                .filter(v -> v.getContent().equals(key))
                .map(Area.class::cast)
                .collect(Collectors.toList());

//        if (memo.getAnchors().containsKey(getVariable())) {
//            memo.getAnchors().get(getVariable()).addAll(anchors);
//        } else {
//            memo.getAnchors().put(getVariable(), anchors.stream().map(Area.class::cast).collect(Collectors.toList()));
//        }
        return anchors;
    }
}
