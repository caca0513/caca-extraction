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

    public List<Visible> split(String key) {
        assert key != null;
        assert !key.isEmpty();
        assert content != null;

        var idx = content.indexOf(key);
        if (idx < 0)
            return List.of(this);

        var result = new ArrayList<Visible>();
        var width = getRight() - getLeft();
        var widthPerChar = width / content.length();

        var from = 0;
        while (idx >= 0) {
            var text = content.substring(from, idx);
            double left = getLeft() + from * widthPerChar;
            double right = left + text.length() * widthPerChar;
            if (!text.isEmpty()) {
                var vis_from = Visible.builder().
                        left(left).
                        right(right).
                        top(getTop()).
                        bottom(getBottom()).
                        content(text).
                        map(getMap()).
                        build();
                result.add(vis_from);
            }

            var vis_key = Visible.builder().
                    left(right).
                    right(right + key.length() * widthPerChar).
                    top(getTop()).
                    bottom(getBottom()).
                    content(key).
                    map(getMap()).
                    build();
            result.add(vis_key);

            from = idx + key.length();
            idx = content.indexOf(key, from);
        }

        if (from < content.length()) {
            var text = content.substring(from);
            double left = getLeft() + from * widthPerChar;
            double right = getRight();
            var vis = Visible.builder().
                    left(left).
                    right(right).
                    top(getTop()).
                    bottom(getBottom()).
                    content(text).
                    map(getMap()).
                    build();
            result.add(vis);
        }

//        var first = content.substring(0, idx);
//        var middle = content.substring(idx, idx + key.length());
//        var last = content.substring(idx + key.length());
//
//
//        double left, right = getLeft();
//
//        for (var txt : List.of(first, middle, last)) {
//            if (txt.isEmpty())
//                continue;
//
//            left = right;
//            right += widthPerChar * txt.length();
//            var vis = Visible.builder().left(left).right(right).top(getTop()).bottom(getBottom()).content(txt).map(getMap()).build();
//            result.add(vis);
//        }

        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " Visible{" +
                "content='" + content + '\'' +
                '}';
    }
}

