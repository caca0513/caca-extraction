package caca.extraction.core.service.impl;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.service.MapLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SROIELoader implements MapLoader<String> {

    private static final Pattern _dataPattern = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(.+)");
    private String ocrFolder;
    private String annotationFolder;

    public SROIELoader(SROIELoaderParameters parameters) {
        this.ocrFolder = parameters.getOCRFolder();
        this.annotationFolder = parameters.getAnnotationFolder();
    }

    @Override
    public TreasureMap load(String source) {
        List<String> ocrResult;
        try {
            ocrResult = Files.readAllLines(Path.of(ocrFolder, source));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        var map = Convert(ocrResult);
        return map;
    }

    private TreasureMap Convert(List<String> ocrTexts) {

        var temp = new ArrayList<Visible>();
        for (var line : ocrTexts) {
            var matcher = _dataPattern.matcher(line);
            if (!matcher.matches())
                continue;

            var result = matcher.toMatchResult();
            var l = Double.parseDouble(result.group(1));
            var t = Double.parseDouble(result.group(2));
            var r = Double.parseDouble(result.group(5));
            var b = Double.parseDouble(result.group(6));
            var text = result.group(9);

            var vis = Visible.builder()
                    .content(text)
                    .left(l)
                    .top(t)
                    .right(r)
                    .bottom(b)
                    .build();
            temp.add(vis);
        }

        double max_width = temp.stream().map(Area::getRight).max(Comparators.comparable()).orElse(0.0);
        double max_height = temp.stream().map(Area::getBottom).max(Comparators.comparable()).orElse(0.0);

        var map = TreasureMap.builder().waypoints(new ArrayList<>()).build();
        temp.forEach(wp ->
                map.getWaypoints().add(
                        Visible.builder()
                                .content(wp.getContent())
                                .left(wp.getLeft() / max_width)
                                .right(wp.getRight() / max_width)
                                .top(wp.getTop() / max_height)
                                .bottom(wp.getBottom() / max_height)
                                .build()
                ));
        return map;
    }
}
