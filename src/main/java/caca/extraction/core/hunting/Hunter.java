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
    private final double intersectThreshold;
    private HuntingStatusEnum status;

    public Hunter(HunterHub hub, String name, TreasureMap map, List<Instruction> instructions, Map<String, Area> anchors, int startStep, double intersectThreshold) {
        {
            if (anchors == null) {
                this.anchors.put("{%OP%}", Areas.OriginPoint);
            } else {
                this.anchors.putAll(anchors);
            }
            this.hub = hub;
            this.name = name;
            this.map = map;
            this.startStep = startStep;
            this.instructions = instructions;
            this.intersectThreshold = intersectThreshold;
            this.status = HuntingStatusEnum.Ready;
        }
    }

    public Hunter(HunterHub hub, String name, TreasureMap map, List<Instruction> instructions) {
        this(hub, name, map, instructions, null, 0, 0.5);
    }

    private void log(String format, Object... args) {
        var msg = String.format("%s %s : %s", Calendar.getInstance().getTime(), name, String.format(format, args));
        logs.add(msg);
        System.out.println(msg);
    }

    public void go() {
        log("MY PRECIOUS~~");
        status = HuntingStatusEnum.Hunting;

        var i = startStep;
        for (; i < instructions.size(); i++) {
            var inst = instructions.get(i);
            var instNo = String.format("%s/%s", i + 1, instructions.size());
            var targetArea = inst.indication(anchors);
            var act = inst.getAction();
            List<Area> finding = act.act(targetArea, map);

            if (finding.size() == 0 || finding.stream().anyMatch(Objects::isNull)) {
                //something is wrong
                log("%s Cannot completed inst: %s, location: %s, target not found: %s", instNo, inst, targetArea, act.getVariable());
                status = HuntingStatusEnum.Blocked;
                break;
            } else if (finding.size() == 1) {
                //usual case
                var newFinding = finding.get(0);
                log("%s Completed inst: %s, location: %s, found: %s", instNo, inst, targetArea, newFinding);
                anchors.put(act.getVariable(), newFinding);
            } else {
                log("%s Completed inst: %s, location: %s, found: %s", instNo, inst, targetArea, finding.size());
                log("Found multiple leads, this one will stop and let's other hunters to help");
                var helperStartStep = i + 1;
                finding.forEach(newFinding -> hub.recruit(this, newFinding, act.getVariable(), helperStartStep).go());
                status = HuntingStatusEnum.Halt;
                break;
            }
        }
        if (i == instructions.size()) {
            //this one run till finished.
            status = HuntingStatusEnum.Completed;
        }
    }

    public String open(String anchorName, TreasureMap map) {
        var targetArea = this.anchors.get(anchorName);
        var target = map.getWaypoints().stream()
                .filter(wp -> wp instanceof Visible)
                //.filter(wp -> Areas.isIntersect(wp, targetArea))
                .filter(wp -> Areas.intersect(wp, targetArea).size() / wp.size() > intersectThreshold)
                .map(Visible.class::cast)
                .map(Visible::getContent)
                .collect(Collectors.toList());
        var result = String.join(" ", target);
        return result;
    }
}
