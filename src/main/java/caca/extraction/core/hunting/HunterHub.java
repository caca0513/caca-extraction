package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;

import java.util.ArrayList;
import java.util.List;

public class HunterHub {
    public static final List<Hunter> hunters = new ArrayList<>();

    public static Hunter recruit(Hunter master, Area lead, String variable) {
        var result = new Hunter(
                master.getName() + " helper " + hunters.size(),
                master.getMap(),
                List.copyOf(master.getInstructions()));
        result.getAnchors().put(variable, lead);

        hunters.add(result);
        return result;
    }

    public static Hunter recruit(TreasureMap map, List<Instruction> insts) {
        var result = new Hunter("hunter " + hunters.size(), map, insts);

        hunters.add(result);
        return result;
    }
}
