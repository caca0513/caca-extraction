package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import caca.extraction.core.models.TreasureMap;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Hunter {
    private final TreasureMap map;
    private final String name;
    //private final Memo memo;
    private final List<Instruction> instructions;
    private final Map<String, Area> anchors = new HashMap<>();
    private final List<String> logs = new ArrayList<>();

    public Hunter(String name, TreasureMap map, List<Instruction> instructions) {
//        if (memo==null) {
//            this.memo = new Memo(this);
//            this.memo.getAnchors().put("{%OP%}", Areas.OriginPoint);
//        } else {
//            this.memo = memo.cloneFor(this);
//        }
        anchors.put("{%OP%}", Areas.OriginPoint);
        this.name = name;
        this.map = map;
        this.instructions = instructions;
    }

    private void log(String format, Object... args) {
        logs.add(String.format("%s %s : %s", Calendar.getInstance().getTime(), name, String.format(format, args)));
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
                finding.forEach(newFinding -> HunterHub.recruit(this, newFinding, act.getVariable()).go());
            }
        }
    }

    public List<Area> open(String anchorName, TreasureMap map) {
        var targetArea = this.anchors.get(anchorName);
        var target = map.getWaypoints().stream().filter(wp -> Areas.isIntersect(wp, targetArea)).collect(Collectors.toList());
        return target;
    }
}
