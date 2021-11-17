package caca.extraction.core.service.impl;

import caca.extraction.core.service.MapLoader;
import caca.extraction.core.service.impl.PaddleSourceLoader;
import lombok.AccessLevel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import java.time.ZoneId;
import java.util.Random;

@Component
@ConfigurationProperties(prefix = "maploader.sorie")
@Data
public class SORIELoaderParameters {

    private String OCRFolder;
    private String AnnotationFolder;

}
