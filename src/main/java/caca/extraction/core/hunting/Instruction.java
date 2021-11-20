package caca.extraction.core.hunting;

import caca.extraction.core.hunting.actions.Action;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.Areas;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Instruction {
    private List<Indicator> indicators = new ArrayList<>();
    private Action action;

    public Area indication(Map<String, Area> currentAnchors) {
        var indicates = indicators.stream()
                .map(i -> {
                    try {
                        return i.indication(currentAnchors);
                    } catch (AnchorNotExistException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        //use the full map as a background target area for the intersection,
        //when no indication given, the full map will be the result
        indicates.add(Areas.Everywhere);

        var result = Areas.intersect(indicates);
        return result;
    }
}
