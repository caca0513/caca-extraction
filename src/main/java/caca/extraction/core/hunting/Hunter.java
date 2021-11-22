package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Hunter {
    private final TreasureMap map;
    private final String name;
    private final HunterHub hub;
    //private final Memo memo;
    private final List<Instruction> instructions;
    private final Map<String, Area> anchors = new HashMap<>();
    private final List<String> logs = new ArrayList<>();

    public Hunter(HunterHub hub, String name, TreasureMap map, List<Instruction> instructions) {
        anchors.put("{%OP%}", Areas.OriginPoint);
        this.hub = hub;
        this.name = name;
        this.map = map;
        this.instructions = instructions;
    }

    private void log(String format, Object... args) {
        var msg = String.format("%s %s : %s", Calendar.getInstance().getTime(), name, String.format(format, args));
        logs.add(msg);
        System.out.println(msg);
    }

    public void go() {
        log("MY PRECIOUS~~");
        for (int i = 0; i < instructions.size(); i++) {
            var inst = instructions.get(i);
            var targetArea = inst.indication(anchors);
            var act = inst.getAction();
            var finding = act.act(targetArea, map);

            if (finding.size() == 0) {
                //something is wrong
                log("Can not follow instruction: " + inst);
                break;
            } else if (finding.size() == 1) {
                //usual case
                var newFinding = finding.get(0);
                log("instruction completed: %s, new finding %s at %s ", inst, act.getVariable(), newFinding);
                anchors.put(act.getVariable(), newFinding);
            } else {
                log("found %s leads, stop and let's other hunters to help", finding.size());
                finding.forEach(newFinding -> hub.recruit(this, newFinding, act.getVariable()).go());
            }
        }
    }

    public String open(String anchorName, TreasureMap map) {
        var targetArea = this.anchors.get(anchorName);
        var target = map.getWaypoints().stream()
                .filter(wp -> Areas.isIntersect(wp, targetArea))
                .filter(wp-> wp instanceof Visible)
                .map(Visible.class::cast)
                .map(Visible::getContent)
                .collect(Collectors.toList());
        var result = String.join(" ", target);
        return result;
    }
}
