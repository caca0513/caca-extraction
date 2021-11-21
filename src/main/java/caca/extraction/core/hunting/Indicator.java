package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Data
public class Indicator {

    private static final Map<String, List<Function<Area, Double>>> _mapping = Map.ofEntries(
            Map.entry("all_left_to_the_left_edge", List.of(a -> 0.0, a -> 0.0, Area::getLeft, a -> 1.0)),
            Map.entry("all_left_to_the_right_edge", List.of(a -> 0.0, a -> 0.0, Area::getRight, a -> 1.0)),
            Map.entry("all_right_to_the_left_edge", List.of(Area::getLeft, a -> 0.0, a -> 1.0, a -> 1.0)),
            Map.entry("all_right_to_the_right_edge", List.of(Area::getRight, a -> 0.0, a -> 1.0, a -> 1.0)),
            Map.entry("all_above_to_the_top_edge", List.of(a -> 0.0, a -> 0.0, a -> 1.0, Area::getTop)),
            Map.entry("all_above_to_the_bottom_edge", List.of(a -> 0.0, a -> 0.0, a -> 1.0, Area::getBottom)),
            Map.entry("all_below_to_the_top_edge", List.of(a -> 0.0, Area::getTop, a -> 1.0, a -> 1.0)),
            Map.entry("all_below_to_the_bottom_edge", List.of(a -> 0.0, Area::getBottom, a -> 1.0, a -> 1.0)),
            Map.entry("just_left_to_the_left_edge", List.of(a -> 0.0, Area::getTop, Area::getLeft, Area::getBottom)),
            Map.entry("just_left_to_the_right_edge", List.of(a -> 0.0, Area::getTop, Area::getRight, Area::getBottom)),
            Map.entry("just_right_to_the_left_edge", List.of(Area::getLeft, Area::getTop, a -> 1.0, Area::getBottom)),
            Map.entry("just_right_to_the_right_edge", List.of(Area::getRight, Area::getTop, a -> 1.0, Area::getBottom)),
            Map.entry("just_above_to_the_top_edge", List.of(Area::getLeft, a -> 0.0, Area::getRight, Area::getTop)),
            Map.entry("just_above_to_the_bottom_edge", List.of(Area::getLeft, a -> 0.0, Area::getRight, Area::getBottom)),
            Map.entry("just_below_to_the_top_edge", List.of(Area::getLeft, Area::getTop, Area::getRight, a -> 1.0)),
            Map.entry("just_below_to_the_bottom_edge", List.of(Area::getLeft, Area::getBottom, Area::getRight, a -> 1.0))
    );

    private static final Map<String, Function<Area, Area>> _indicationMapping;

    static {
        var temp = new HashMap<String, Function<Area, Area>>();
        var alls = List.of("just", "all");
        var directions = List.of(
                new String[]{"left", "left"},
                new String[]{"left", "right"},
                new String[]{"right", "left"},
                new String[]{"right", "right"},
                new String[]{"above", "top"},
                new String[]{"above", "bottom"},
                new String[]{"below", "top"},
                new String[]{"below", "bottom"}
        );
        for (var a : alls)
            for (var dir : directions) {
                var key = String.format("%s_%s_to_the_%s_edge", a, dir[0], dir[1]);
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
    private final IndicatorDirection direction;
    private final IndicatorEdge edge;
    private final double offset;
    private final String anchorName;
    private final String indicationKey;

    public Indicator(String all, String direction, String edge, String offset, String variable) {
        this.all = all == null ? "just" : all.trim().toLowerCase();
        this.offset = offset == null ? 0 : Double.parseDouble(offset.trim());
        this.anchorName = variable;
        this.direction = IndicatorDirection.valueOf(direction.toLowerCase().trim());
        if (edge == null) {
            switch (this.direction) {
                case left:
                    this.edge = IndicatorEdge.valueOf("left");
                    break;
                case right:
                    this.edge = IndicatorEdge.valueOf("right");
                    break;
                case above:
                    this.edge = IndicatorEdge.valueOf("top");
                    break;
                case below:
                    this.edge = IndicatorEdge.valueOf( "bottom");
                    break;
                default:
                    assert true; //this should never happen
                    this.edge = null;
            }
        } else {
            this.edge = IndicatorEdge.valueOf(edge.trim());
        }
        indicationKey = String.format("%s_%s_to_the_%s_edge", this.all, this.direction, this.edge);
    }


    public Area indication(Map<String, Area> currentAnchors) throws AnchorNotExistException, IndicationNotValidException {
        if (!currentAnchors.containsKey(anchorName)) {
            throw new AnchorNotExistException(anchorName);
        }

        var anchor = currentAnchors.get(anchorName);
        var indicateFunction = _indicationMapping.get(indicationKey);
        if (indicateFunction == null) {
            throw new IndicationNotValidException(indicationKey);
        }
        var indicate = indicateFunction.apply(anchor);
        indicate = applyOffset(indicate);

        return indicate;
    }

    private Area applyOffset(Area area) {
        Area result;

        switch (direction) {
            case below:
                result = new Area(area.getLeft(), area.getTop() + offset, area.getRight(), area.getBottom());
                break;
            case above:
                result = new Area(area.getLeft(), area.getTop(), area.getRight(), area.getBottom() + offset);
                break;
            case left:
                result = new Area(area.getLeft(), area.getTop(), area.getRight() + offset, area.getBottom());
                break;
            case right:
                result = new Area(area.getLeft() + offset, area.getTop(), area.getRight(), area.getBottom());
                break;
            default:
                result = area;
        }

        return result;
    }
}
