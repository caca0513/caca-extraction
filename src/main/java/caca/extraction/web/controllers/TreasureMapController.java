package caca.extraction.web.controllers;

import caca.extraction.core.models.MapDisplayOptions;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.repo.TreasureMapRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class TreasureMapController {

    private TreasureMapRepo mapRepo;
    private MapDisplayOptions displayOptions;

    @GetMapping("/{id}")
    public String showMap(@PathVariable Long id, Model model) {
        var map = mapRepo.findById(id).get();

        var newMap = ConvertCoordinate(map, displayOptions);

        model.addAttribute("map", newMap);
        model.addAttribute("options", displayOptions);

        return "map";
    }

    private TreasureMap ConvertCoordinate(TreasureMap map, MapDisplayOptions displayOptions) {
        var max_width = map.getWaypoints().stream().map(wp -> wp.getRight()).max(Comparators.comparable()).get();
        var max_height = map.getWaypoints().stream().map(wp -> wp.getRight()).max(Comparators.comparable()).get();

        var newMap = TreasureMap.builder()
                .id(map.getId())
                .name(map.getName())
                .waypoints(new ArrayList<>())
                .build();

        map.getWaypoints().forEach(wp ->
                newMap.getWaypoints().add(
                        Visible.builder()
                                .content(wp instanceof Visible ? ((Visible) wp).getContent() : "")
                                .left(wp.getLeft() / max_width)
                                .right(wp.getRight() / max_width)
                                .top(wp.getTop() / max_height)
                                .bottom(wp.getBottom() / max_height)
                                .build()
                ));

        return newMap;
    }
}
