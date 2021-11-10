package caca.extraction.web.controllers;

import caca.extraction.core.repo.TreasureMapRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class TreasureMapController {

    private TreasureMapRepo mapRepo;

    @GetMapping
    public String showMap(Model model) {
        var maps = mapRepo.findAll();
        var map = maps.iterator().next();

        model.addAttribute("map", map);
        model.addAttribute("map_width", 800);
        model.addAttribute("map_height", 600);

        return "map";
    }
}
