package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;

import java.util.ArrayList;
import java.util.List;

public class HunterHub {
    public final List<Hunter> hunters = new ArrayList<>();

    public Hunter recruit(Hunter master, Area lead, String variable, int startStep) {
        var result = new Hunter(
                this,
                master.getName() + " helper " + hunters.size(),
                master.getMap(),
                List.copyOf(master.getInstructions()),
                master.getAnchors(),
                startStep, master.getIntersectThreshold());
        result.getAnchors().put(variable, lead);

        hunters.add(result);
        return result;
    }

    public Hunter recruit(TreasureMap map, List<Instruction> insts) {
        var result = new Hunter(this, "hunter " + hunters.size(), map, insts);

        hunters.add(result);
        return result;
    }
}
