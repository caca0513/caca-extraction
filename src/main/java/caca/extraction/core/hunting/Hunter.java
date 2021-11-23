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
    private final int startStep;

    public Hunter(HunterHub hub, String name, TreasureMap map, List<Instruction> instructions, int startStep) {
        {
            anchors.put("{%OP%}", Areas.OriginPoint);
            this.hub = hub;
            this.name = name;
            this.map = map;
            this.startStep = startStep;
            this.instructions = instructions;
        }
    }

    public Hunter(HunterHub hub, String name, TreasureMap map, List<Instruction> instructions) {
        this(hub, name, map, instructions, 0);
    }

    private void log(String format, Object... args) {
        var msg = String.format("%s %s : %s", Calendar.getInstance().getTime(), name, String.format(format, args));
        logs.add(msg);
        System.out.println(msg);
    }

    public void go() {
        log("MY PRECIOUS~~");
        for (int i = startStep; i < instructions.size(); i++) {
            var inst = instructions.get(i);
            var instNo = String.format("%s/%s", i + 1, instructions.size());
            var targetArea = inst.indication(anchors);
            var act = inst.getAction();
            List<Area> finding = act.act(targetArea, map);

            if (finding.size() == 0 || finding.stream().anyMatch(Objects::isNull)) {
                //something is wrong
                log("%s Can not complete Instruction: %s", instNo, inst);
                log("%s Anchor not found: %s", instNo, act.getVariable());
                break;
            } else if (finding.size() == 1) {
                //usual case
                var newFinding = finding.get(0);
                log("%s instruction completed: %s, new finding %s at %s ", instNo, inst, act.getVariable(), newFinding);
                anchors.put(act.getVariable(), newFinding);
            } else {
                log("%s instruction %s results %s leads, stop and let's other hunters to help", instNo, inst, finding.size());
                int startStep = i;
                finding.forEach(newFinding -> hub.recruit(this, newFinding, act.getVariable(), startStep + 1).go());
            }
        }
    }

    public String open(String anchorName, TreasureMap map) {
        var targetArea = this.anchors.get(anchorName);
        var target = map.getWaypoints().stream()
                .filter(wp -> Areas.isIntersect(wp, targetArea))
                .filter(wp -> wp instanceof Visible)
                .map(Visible.class::cast)
                .map(Visible::getContent)
                .collect(Collectors.toList());
        var result = String.join(" ", target);
        return result;
    }
}
