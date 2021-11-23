package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HunterHubTest {

    @Test
    void recruit() {
        var hub = new HunterHub();
        var map = new TreasureMap();
        var inst = List.of(
                new Instruction(),
                new Instruction(),
                new Instruction()
        );
        var master = hub.recruit(map, inst);
        master.getAnchors().put("ank1", new Area(0,1,0,1));

        var helper = hub.recruit(master, new Area(0,0,1,1), "test", 1);

        var originAnchors = master.getAnchors();
        var newAnchors = helper.getAnchors();
        Assertions.assertThat(originAnchors).hasSize(2);
        Assertions.assertThat(newAnchors).hasSize(3);
    }
}