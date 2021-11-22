package caca.extraction.core.models;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class VisibleTest {

    @Test
    void split() {
        var base = buildVisual(new Object[]{0.1, 0.1, 0.5, 0.3, "1234567890"});

        var actual = base.split("123");
        var expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.22, 0.3, "123"}),
                buildVisual(new Object[]{0.22, 0.1, 0.5, 0.3, "4567890"})
        );
        ElementCompare(actual, expectation);

        actual = base.split("456");
        expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.22, 0.3, "123"}),
                buildVisual(new Object[]{0.22, 0.1, 0.34, 0.3, "456"}),
                buildVisual(new Object[]{0.34, 0.1, 0.5, 0.3, "7890"})
        );
        ElementCompare(actual, expectation);

        actual = base.split("890");
        expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.38, 0.3, "1234567"}),
                buildVisual(new Object[]{0.38, 0.1, 0.5, 0.3, "890"})
        );
        ElementCompare(actual, expectation);


        actual = base.split("abcd");
        expectation = Lists.newArrayList(
                base
        );
        ElementCompare(actual, expectation);
    }

    private void ElementCompare(List<Visible> actual, ArrayList<Visible> expectation) {
        //won't work //Assertions.assertThat(test).hasSameElementsAs(expectation);
        Assertions.assertThat(actual).hasSameSizeAs(expectation);
        for (var i = 0; i < actual.size(); i++)
            Assertions.assertThat(actual.get(i)).isEqualToComparingFieldByField(expectation.get(i));
    }

    private Visible buildVisual(Object[] test) {
        var vis = Visible.builder()
                .left((Double) test[0])
                .top((Double) test[1])
                .right((Double) test[2])
                .bottom((Double) test[3])
                .content((String) test[4])
                .build();
        return vis;
    }
}