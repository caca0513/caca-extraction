package caca.extraction.core.service.impl;

import caca.extraction.common.FileHelper;
import caca.extraction.core.models.Area;
import caca.extraction.core.models.SROIEAnnotation;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.service.MapLoader;
import com.alibaba.fastjson.JSON;
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
    private final String ocrFolder;
    private String annotationFolder;

    public SROIELoader(SROIELoaderParameters parameters) {
        this.ocrFolder = parameters.getOCRFolder();
        this.annotationFolder = parameters.getAnnotationFolder();
    }

    @Override
    public TreasureMap load(String source) {

        List<String> ocrResult = FileHelper.readTxt(ocrFolder, source);
        assert ocrResult != null;
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

        return TreasureMap.convertToTreasureMap(temp);
    }


    public SROIEAnnotation loadAnnotation(String source) {
        String content;
        try {
            if (!source.toLowerCase().endsWith(".txt"))
                source += ".txt";
            content = Files.readString(Path.of(annotationFolder, source));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        var result = JSON.parseObject(content, SROIEAnnotation.class);
        return result;
    }
}
