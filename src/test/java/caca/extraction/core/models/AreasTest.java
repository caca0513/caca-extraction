package caca.extraction.core.models;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class AreasTest {

    @Test
    void intersectTest1() {
        var result = Areas.intersect(List.of(
                new Area(0.4, 0.0, 0.6, 1.0),
                new Area(0.0, 0.4, 1.0, 0.6),
                new Area(0.2, 0.2, 0.5, 0.5)
        ));
        var expected = new Area(0.4, 0.4, 0.5, 0.5);

        Assert.notNull(result, "");
        Assert.isTrue(AreaEqual(expected, result), "");
    }

    @Test
    void intersectTest2() {
        var result = Areas.intersect(List.of(
                new Area(0.4, 0.0, 0.6, 1.0),
                new Area(0.0, 0.4, 1.0, 0.6),
                new Area(0.2, 0.2, 0.3, 0.3)
        ));

        Assert.isNull(result, "");
    }

    public boolean AreaEqual(Area area1, Area area2) {
        return area1.getLeft() == area2.getLeft()
                && area1.getTop() == area2.getTop()
                && area1.getRight() == area2.getRight()
                && area1.getBottom() == area2.getBottom()
                ;
    }
}