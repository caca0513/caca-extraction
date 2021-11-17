package caca.extraction.core.service.impl;

import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.service.MapLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SORIELoader implements MapLoader<String> {

    private String ocrFolder;
    private String annotationFolder;

    public SORIELoader(SORIELoaderParameters parameters) {
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
        var map = TreasureMap.builder().waypoints(new ArrayList<>()).build();
        var pattern = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(.+)");
        for (var line : ocrTexts) {
            var matcher = pattern.matcher(line);
            if (!matcher.matches())
                continue;

            var result = matcher.toMatchResult();
            var l = Double.parseDouble(result.group(1));
            var t = Double.parseDouble(result.group(2));
            var r = Double.parseDouble(result.group(5));
            var b = Double.parseDouble(result.group(6));
            var text = result.group(9);

            var vis = Visible.builder()
                    .map(map)
                    .content(text)
                    .left(l)
                    .top(t)
                    .right(r)
                    .bottom(b)
                    .build();
            map.getWaypoints().add(vis);
        }
        return map;
    }
}
