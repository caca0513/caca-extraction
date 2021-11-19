package caca.extraction.core.hunting.actions;

import caca.extraction.core.hunting.Memo;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;

import java.util.stream.Collectors;

public class FindAction extends Action {

    public FindAction(String variable) {
        super(variable);
    }

    @Override
    public void act(Area targetArea, Memo memo, TreasureMap map) {
        var anchors = map.getWaypoints().stream()
                .filter(Visible.class::isInstance)
                .filter(wp -> Areas.isIntersect(wp, targetArea))
                .map(Visible.class::cast)
                .filter(v -> v.getContent().equals(getVariable()))
                .collect(Collectors.toList());

        if (memo.getAnchors().containsKey(getVariable())) {
            memo.getAnchors().get(getVariable()).addAll(anchors);
        } else {
            memo.getAnchors().put(getVariable(), anchors.stream().map(Area.class::cast).collect(Collectors.toList()));
        }
    }
}
