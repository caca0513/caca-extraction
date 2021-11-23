package caca.extraction.core.models;

import java.util.Collection;

public class Areas {
    public static final Area OriginPoint = new Area(0, 0, 0, 0);
    public static final Area Everywhere = new Area(0, 0, 1, 1);

    public static Area intersect(Collection<Area> areas) {
        var result = areas.stream().reduce(Areas::intersect).get();
        return result.size() > 0 ? result : null;
    }

    public static Area intersect(Area area1, Area area2) {
        var l = Math.max(area1.getLeft(), area2.getLeft());
        var t = Math.max(area1.getTop(), area2.getTop());
        var r = Math.min(area1.getRight(), area2.getRight());
        var b = Math.min(area1.getBottom(), area2.getBottom());

        return new Area(l, t, r, b);
    }

    public static boolean isIntersect(Area area1, Area area2) {
        return intersect(area1, area2).size() > 0;
    }
}
