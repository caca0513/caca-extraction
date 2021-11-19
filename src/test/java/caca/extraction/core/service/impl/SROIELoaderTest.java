package caca.extraction.core.service.impl;

import caca.extraction.PathFinderApplication;
import caca.extraction.core.models.Visible;
import caca.extraction.core.service.MapLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@SpringBootTest(classes = {PathFinderApplication.class})
class SROIELoaderTest {

    @Resource(name = "sorie")
    private MapLoader<String> loader;

    @Test
    void load() {
        var map = loader.load("X51005453802.txt");

        Assert.notNull(map, "map should be loaded");
        Assert.notNull(map.getWaypoints(), "waypoints should not be null");
        Assert.isTrue(map.getWaypoints().size() == 48, "should be 48 waypoints");
        var wp1 = map.getWaypoints().get(1);
        Assert.isTrue(wp1 instanceof Visible, "waypoint 1 should be Visible");
        Assert.isTrue(((Visible) wp1).getContent().equals("LIAN HING STATIONERY SDN BHD"), "should be match");
        Assert.isTrue(wp1.getId() == null, "should be match");
        Assert.isTrue(wp1.getLeft() == 150.0, "should be match");
        Assert.isTrue(wp1.getTop() == 133.0, "should be match");
        Assert.isTrue(wp1.getRight() == 472.0, "should be match");
        Assert.isTrue(wp1.getBottom() == 154.0, "should be match");
    }
}