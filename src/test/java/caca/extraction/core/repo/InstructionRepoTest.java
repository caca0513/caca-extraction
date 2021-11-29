package caca.extraction.core.repo;

import caca.extraction.PathFinderApplication;
import caca.extraction.core.hunting.actions.DefineAction;
import caca.extraction.core.hunting.actions.FindAction;
import caca.extraction.core.repo.impl.FileInstructionRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

class InstructionRepoTest {

//    @Autowired
//    InstructionRepo repo;

    @Test
    void testLoad() {
        var repo = new FileInstructionRepo();
        repo.setFolder("E:\\Projects\\Java\\TreasureHunting\\Instructions");

        var instructions = repo.load("X51005453802.txt");
        Assert.notNull(instructions, "instructions should not be null");

        var expected = List.of(
                List.of(2, DefineAction.class),
                List.of(0, FindAction.class),
                List.of(1, FindAction.class),
                List.of(1, DefineAction.class),
                List.of(0, FindAction.class),
                List.of(1, DefineAction.class),
                List.of(0, FindAction.class),
                List.of(0, FindAction.class),
                List.of(2, DefineAction.class)
        );

        Assert.isTrue(instructions.size() == expected.size(), "instructions size should not be " + expected.size());
        for (int i = 0; i < instructions.size(); i++) {
            Assert.isTrue(instructions.get(i).getIndicators().size() == (Integer) expected.get(i).get(0), String.format("instruction no %s should have %s indicators, actual %s", i, expected.get(i).get(0), instructions.get(i).getIndicators().size()));
            Assert.notNull(instructions.get(i).getAction(), String.format("instruction no %s action should not be null", i));
            Assert.isTrue(instructions.get(i).getAction().getClass() == expected.get(i).get(1), String.format("instruction no %s should have action type %s, actual %s", i, expected.get(i).get(0), instructions.get(i).getIndicators().size()));
        }
    }
}