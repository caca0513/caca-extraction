package caca.extraction.core.models;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class VisibleTest {
    @Test
    void split_at_begin() {
        var base = buildVisual(new Object[]{0.1, 0.1, 0.5, 0.3, "1234567890"});

        var actual = base.split("123");
        var expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.22, 0.3, "123"}),
                buildVisual(new Object[]{0.22, 0.1, 0.5, 0.3, "4567890"})
        );
        ElementCompare(actual, expectation);
    }


    @Test
    void split_in_middle() {
        var base = buildVisual(new Object[]{0.1, 0.1, 0.5, 0.3, "1234567890"});

        var actual = base.split("456");
        var expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.22, 0.3, "123"}),
                buildVisual(new Object[]{0.22, 0.1, 0.34, 0.3, "456"}),
                buildVisual(new Object[]{0.34, 0.1, 0.5, 0.3, "7890"})
        );
        ElementCompare(actual, expectation);
    }

    @Test
    void split_at_ending() {
        var base = buildVisual(new Object[]{0.1, 0.1, 0.5, 0.3, "1234567890"});

        var actual = base.split("890");
        var expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.1, 0.1, 0.38, 0.3, "1234567"}),
                buildVisual(new Object[]{0.38, 0.1, 0.5, 0.3, "890"})
        );
        ElementCompare(actual, expectation);
    }

    @Test
    void split_not_exist() {
        var base = buildVisual(new Object[]{0.1, 0.1, 0.5, 0.3, "1234567890"});

        var actual = base.split("abcd");
        var expectation = Lists.newArrayList(
                base
        );
        ElementCompare(actual, expectation);
    }

    @Test
    void split_multiple_occurrences() {
        var base = buildVisual(new Object[]{0.06, 0.1, 0.54, 0.3, "312345638303"});

        var actual = base.split("3");
        var expectation = Lists.newArrayList(
                buildVisual(new Object[]{0.06, 0.1, 0.10, 0.3, "3"}),
                buildVisual(new Object[]{0.10, 0.1, 0.18, 0.3, "12"}),
                buildVisual(new Object[]{0.18, 0.1, 0.22, 0.3, "3"}),
                buildVisual(new Object[]{0.22, 0.1, 0.34, 0.3, "456"}),
                buildVisual(new Object[]{0.34, 0.1, 0.38, 0.3, "3"}),
                buildVisual(new Object[]{0.38, 0.1, 0.42, 0.3, "8"}),
                buildVisual(new Object[]{0.42, 0.1, 0.46, 0.3, "3"}),
                buildVisual(new Object[]{0.46, 0.1, 0.50, 0.3, "0"}),
                buildVisual(new Object[]{0.50, 0.1, 0.54, 0.3, "3"})
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