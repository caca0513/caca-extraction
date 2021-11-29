package caca.extraction.core.service.impl;

import caca.extraction.core.models.Visible;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class SROIELoaderTest {

    @Test
    void load() {
        var parameters = new SROIELoaderParameters();
        parameters.setOCRFolder("E:\\ML-Data\\SROIE2019\\0325updated.task1train(626p)");
        parameters.setAnnotationFolder("E:\\ML-Data\\SROIE2019\\0325updated.task2train(626p)");

        var loader = new SROIELoader(parameters);

        var map = loader.load("X51005453802.txt");

        Assert.notNull(map, "map should be loaded");
        Assert.notNull(map.getWaypoints(), "waypoints should not be null");
        Assertions.assertThat(map.getWaypoints().size()).isEqualTo(112);
        var wp1 = map.getWaypoints().get(1);
        Assert.isTrue(wp1 instanceof Visible, "waypoint 1 should be Visible");
        Assertions.assertThat(((Visible) wp1).getContent()).isEqualTo("LIAN");
        Assert.isTrue(wp1.getId() == null, "should be match");
        //the load function should convert the position into relative figure,
        //relate to the maximum width/height of origin image
        Assert.isTrue(wp1.getLeft() == 150.0 / 525.0, "should be match");
        Assert.isTrue(wp1.getTop() == 133.0 / 910.0, "should be match");
        Assertions.assertThat(wp1.getRight()).isEqualTo((150.0 + (472.0 - 150.0) * 4 / 28) / 525.0);
        Assert.isTrue(wp1.getBottom() == 154.0 / 910.0, "should be match");
    }
}