package caca.extraction.core.hunting;

import caca.extraction.core.models.TreasureMap;

import java.util.List;

public class Hunter {

    private final Memo memo = new Memo();

    public void go(List<Instruction> instructions, TreasureMap map) {
        for (int i = 0; i < instructions.size(); i++) {
            var inst = instructions.get(i);
            var targetAreas = inst.indication(memo);
            targetAreas.forEach(targetArea -> inst.getAction().act(targetArea, memo, map));
        }
    }
}
