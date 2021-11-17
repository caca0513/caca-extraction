package caca.extraction.web.controllers;

import caca.extraction.RequestParameters;
import caca.extraction.core.Indicators;
import caca.extraction.core.models.Area;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class TreasureMapController {

    private TreasureMapRepo mapRepo;
    private RequestParameters options;
    private RestTemplate rest;

    @GetMapping("/{id}")
    public String showMap(@PathVariable Long id, Model model) {
        var map = mapRepo.findById(id).get();

        var newMap = ConvertCoordinate(map);
        model.addAttribute("map", newMap);
        model.addAttribute("options", options);

        return "map";
    }

    @GetMapping("now")
    public String showTime(Model model) {
        var date = GetDate();
        model.addAttribute("date", date);

        return "time";
    }

    public void walk(){

//        List<Area> waypoints = new ArrayList<>();
//        List<Indicators> path = new ArrayList<>();
//        var startPoint = new Area(0,0,0,0);
//        for (var indicator : path             ) {
//            startPoint = indicator.applyOn(startPoint, waypoints);
//        }


    }


    private String GetDate() {
        log.info("Requesting time in timezone: " + options.getTimezone());
        var result = rest.getForObject("http://time-service/time/now/{timezone}", String.class, options.getTimezone());
        return result;
    }

    private TreasureMap ConvertCoordinate(TreasureMap map) {
        double max_width = map.getWaypoints().stream().map(Area::getRight).max(Comparators.comparable()).orElseGet(()->(double)options.getWidth());
        double max_height = map.getWaypoints().stream().map(Area::getBottom).max(Comparators.comparable()).orElseGet(()->(double)options.getHeight());

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
