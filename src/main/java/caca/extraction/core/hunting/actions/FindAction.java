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
        var key = getVariable().substring(1, getVariable().length() - 1);
        var anchors = map.getWaypoints().stream()
                .filter(Visible.class::isInstance)
                .filter(wp -> Areas.isIntersect(wp, targetArea))
                .map(Visible.class::cast)
                .filter(v -> v.getContent().contains(key))   //use contain for partial match
                //.map(Area.class::cast)
                .collect(Collectors.toList());

        //if a Visual's content contains the keyword, split the keyword from the content,
        //and creating new Visuals
        var containKeywordButNotEqual = anchors.stream().filter(v -> !v.getContent().equals(key)).collect(Collectors.toList());
        for (var vis : containKeywordButNotEqual) {
            //split in the map
            var temp = map.split(vis, key);
            var visForVar = temp.stream().filter(v -> v.getContent().equals(key)).findFirst().orElse(null);
            //use the new Visual that has the exact keyword as content,
            //to replace the origin matched one that CONTAINS the keyword in its content
            anchors.remove(vis);
            anchors.add(visForVar);
        }

        return anchors.stream().map(Area.class::cast).collect(Collectors.toList());
    }
}
