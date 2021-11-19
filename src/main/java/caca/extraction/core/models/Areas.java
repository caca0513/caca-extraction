package caca.extraction.core.models;

import java.util.Collection;

public class Areas {
    public static Area intersect(Collection<Area> areas) {
        var result = areas.stream().reduce(Areas::intersect).get();
        return result.size() > 0 ? result : null;
    }

    private static Area intersect(Area area1, Area area2) {
        var l = Math.max(area1.getLeft(), area2.getLeft());
        var t = Math.max(area1.getTop(), area2.getTop());
        var r = Math.max(area1.getRight(), area2.getRight());
        var b = Math.max(area1.getBottom(), area2.getBottom());

        return new Area(l, t, r, b);
    }

    public static boolean isIntersect(Area area1, Area area2) {
        return intersect(area1, area2).size() > 0;
    }
}
