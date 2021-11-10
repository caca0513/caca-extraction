package caca.extraction.core.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MapDisplayOptions {
    private int width = 800;
    private int height = 600;
}
