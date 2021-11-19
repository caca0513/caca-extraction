package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Memo {
    private final Map<String, List<Area>> anchors = new HashMap<>();
}
