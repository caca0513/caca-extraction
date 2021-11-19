package caca.extraction.core.service.impl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "maploader.sorie")
@Data
public class SROIELoaderParameters {

    private String OCRFolder;
    private String AnnotationFolder;

}
