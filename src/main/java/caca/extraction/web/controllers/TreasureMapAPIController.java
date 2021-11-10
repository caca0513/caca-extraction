package caca.extraction.web.controllers;

import caca.extraction.core.models.paddle.PaddleOCRText;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.repo.TreasureMapRepo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/map", produces = "application/json")
public class TreasureMapAPIController {

    @Autowired
    private TreasureMapRepo mapRepo;

    @GetMapping(value = "/add")
    public TreasureMap add(String name) throws IOException {
        var p = Paths.get(name);
        if (Files.isRegularFile(p)) {
            log.info("File found: " + p.toAbsolutePath());
        } else {
            log.info("File not found: " + p.toAbsolutePath());
            return null;
        }

        var content = Files.readString(p);
        var result = JSON.parseArray(content, PaddleOCRText.class);

        var map = Convert(result);
        map.setName(name);
        mapRepo.save(map);

        map = mapRepo.getById(map.getId());

        return map;
    }

    public TreasureMap Convert(List<PaddleOCRText> ocrTexts){
        var map = TreasureMap.builder().waypoints(new ArrayList<>()).build();
        for (var text : ocrTexts             ) {
            var vis = Visible.builder()
                    .map(map)
                    .content(text.getText())
                    .left(Double.parseDouble(text.getLt().getX()))
                    .top(Double.parseDouble(text.getLt().getY()))
                    .right(Double.parseDouble(text.getRb().getX()))
                    .bottom(Double.parseDouble(text.getRb().getY()))
                    .build();
            map.getWaypoints().add(vis);
        }
        return map;
    }
}
