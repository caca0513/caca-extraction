package caca.extraction.core.service.impl;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.models.paddle.PaddleOCRText;
import caca.extraction.core.service.MapLoader;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaddleSourceLoader implements MapLoader<Path> {

    @Override
    public TreasureMap load(Path path) {
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        var result = JSON.parseArray(content, PaddleOCRText.class);
        var map = Convert(result);
        return map;
    }

    private TreasureMap Convert(List<PaddleOCRText> ocrTexts) {
        var temp = new ArrayList<Visible>();
        for (var text : ocrTexts) {
            var vis = Visible.builder()
                    .content(text.getText())
                    .left(Double.parseDouble(text.getLt().getX()))
                    .top(Double.parseDouble(text.getLt().getY()))
                    .right(Double.parseDouble(text.getRb().getX()))
                    .bottom(Double.parseDouble(text.getRb().getY()))
                    .build();
            temp.add(vis);
        }

        return TreasureMap.convertToTreasureMap(temp);
    }
}
