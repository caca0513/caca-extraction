package caca.extraction.core.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.util.comparator.Comparators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "TREASUREMAP")
public class TreasureMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double width;
    private double height;

    @OneToMany(mappedBy = "map", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Area> waypoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TreasureMap that = (TreasureMap) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<Visible> split(Visible vis, String text) {
        var result = vis.split(text);
        //replace the origin element with slices
        waypoints.remove(vis);
        waypoints.addAll(result);
        return result;
    }

    public static TreasureMap convertToTreasureMap(List<Visible> temp) {
        double max_width = temp.stream().map(Area::getRight).max(Comparators.comparable()).orElse(0.0);
        double max_height = temp.stream().map(Area::getBottom).max(Comparators.comparable()).orElse(0.0);

        var map = TreasureMap.builder().waypoints(new ArrayList<>()).width(max_width).height(max_height).build();
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

