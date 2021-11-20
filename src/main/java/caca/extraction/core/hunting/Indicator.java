package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
@AllArgsConstructor
public class Indicator {

    private static final Map<String, List<Function<Area, Double>>> _mapping = Map.of(
            "all_left", List.of(a -> 0.0, a -> 0.0, Area::getLeft, a -> 1.0),
            "all_right", List.of(Area::getRight, a -> 0.0, a -> 1.0, a -> 1.0),
            "all_above", List.of(a -> 0.0, a -> 0.0, a -> 1.0, Area::getTop),
            "all_below", List.of(a -> 0.0, Area::getBottom, a -> 1.0, a -> 1.0),
            "null_left", List.of(a -> 0.0, Area::getTop, Area::getLeft, Area::getBottom),
            "null_right", List.of(Area::getRight, Area::getTop, a -> 1.0, Area::getBottom),
            "null_above", List.of(Area::getLeft, a -> 0.0, Area::getRight, Area::getTop),
            "null_below", List.of(Area::getLeft, Area::getBottom, Area::getRight, a -> 1.0)
    );

    private static final Map<String, Function<Area, Area>> _indicationMapping;

    static {
        var temp = new HashMap<String, Function<Area, Area>>();
        var alls = List.of("null", "all");
        var directions = List.of("left", "right", "above", "below");
        for (var a : alls)
            for (var dir : directions) {
                var key = String.format("%s_%s", a, dir);
                Function<Area, Area> func = ank -> Area.builder()
                        .left(_mapping.get(key).get(0).apply(ank))
                        .top(_mapping.get(key).get(1).apply(ank))
                        .right(_mapping.get(key).get(2).apply(ank))
                        .bottom(_mapping.get(key).get(3).apply(ank))
                        .build();
                temp.put(key, func);
            }

        _indicationMapping = Map.copyOf(temp);
    }

    private final String all;
    private final String direction;
    private final String detail;
    private final String offset;
    private final String anchorName;

    private String _indicationKey() {
        return String.format("%s_%s", all == null ? null : all.trim(), direction == null ? null : direction.trim()).toLowerCase();
    }

    public Area indication(Map<String, Area> currentAnchors) throws AnchorNotExistException {
        if (!currentAnchors.containsKey(anchorName)) {
            throw new AnchorNotExistException(anchorName);
        }

        var anchor = currentAnchors.get(anchorName);
        var key = _indicationKey();
        var indicateFunction = _indicationMapping.get(key);
        var indicate = indicateFunction.apply(anchor);

        return indicate;
    }
}
