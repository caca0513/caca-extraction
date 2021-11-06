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
        var map = mapRepo.findById(1L);
//        var tMap = TreasureMap.builder().waypoints(Arrays.asList(
//                Visible.builder().left(0.1).top(0.1).right(0.5).bottom(0.2).content("world").build(),
//                Visible.builder().left(0.6).top(0.1).right(1.0).bottom(0.2).content("hello").build(),
//                Visible.builder().left(0.1).top(0.3).right(0.45).bottom(0.4).content("let's").build(),
//                Visible.builder().left(0.5).top(0.3).right(0.95).bottom(0.4).content("tangle").build()
//        )).build();

        model.addAttribute("map", map);
        model.addAttribute("map_width", 800);
        model.addAttribute("map_height", 600);

        return "map";
    }
}
