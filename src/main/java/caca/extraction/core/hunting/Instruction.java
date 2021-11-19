package caca.extraction.core.hunting;

import caca.extraction.core.hunting.actions.Action;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Instruction {
    private List<Indicator> indicators = new ArrayList<>();
    private Action action;

    public List<Area> indication(Memo memo) {
        var allAreas = indicators.stream().map(i->i.indication(memo)).collect(Collectors.toList());
        return allAreas.stream().map(Areas::intersect).collect(Collectors.toList());
    }
}
