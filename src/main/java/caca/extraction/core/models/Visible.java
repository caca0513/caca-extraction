package caca.extraction.core.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
public class Visible extends Area {
    private final String content;

    public List<Visible> split(String text) {
        assert text != null;
        assert content != null;

        var idx = content.indexOf(text);
        if (idx < 0)
            return List.of(this);

        var first = content.substring(0, idx);
        var middle = content.substring(idx, idx + text.length());
        var last = content.substring(idx + text.length());

        var width = getRight() - getLeft();
        var widthPerChar = width / content.length();

        double left, right = getLeft();
        var result = new ArrayList<Visible>();
        for (var txt : List.of(first, middle, last)) {
            if (txt.isEmpty())
                continue;

            left = right;
            right += widthPerChar * txt.length();
            var vis = Visible.builder().left(left).right(right).top(getTop()).bottom(getBottom()).content(txt).map(getMap()).build();
            result.add(vis);
        }

        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " Visible{" +
                "content='" + content + '\'' +
                '}';
    }
}

