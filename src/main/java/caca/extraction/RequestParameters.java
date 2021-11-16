package caca.extraction;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
public class RequestParameters {
    private int width;
    private int height;
    private String timezone;
}
