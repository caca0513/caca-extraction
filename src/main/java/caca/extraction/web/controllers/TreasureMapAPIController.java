package caca.extraction.web.controllers;

import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.repo.TreasureMapRepo;
import caca.extraction.core.service.MapLoader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "/map", produces = "application/json")
public class TreasureMapAPIController {

    private TreasureMapRepo mapRepo;
    @Resource(name = "paddle")
    private MapLoader<Path> paddle;

    @Resource(name = "sorie")
    private MapLoader<String> sorie;

    @GetMapping(value = "/add/paddle/{name}")
    public TreasureMap addPaddle(@PathVariable String name) {
        var p = Paths.get(name);
        if (Files.isRegularFile(p)) {
            log.info("File found: " + p.toAbsolutePath());
        } else {
            log.info("File not found: " + p.toAbsolutePath());
            return null;
        }

        var map = paddle.load(p);

        map.setName(name);
        mapRepo.save(map);

        map = mapRepo.getById(map.getId());
        return map;
    }


    @GetMapping(value = "/add/sorie/{name}")
    public TreasureMap addSORIE(@PathVariable String name) {

        var map = sorie.load(name);

//        map.setName(name);
//        mapRepo.save(map);
//        map = mapRepo.getById(map.getId());

        return map;
    }


}
