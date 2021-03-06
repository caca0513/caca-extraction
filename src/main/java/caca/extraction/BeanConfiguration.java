package caca.extraction;

import caca.extraction.core.service.MapLoader;
import caca.extraction.core.service.impl.PaddleSourceLoader;
import caca.extraction.core.service.impl.SROIELoader;
import caca.extraction.core.service.impl.SROIELoaderParameters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import java.nio.file.Path;
import java.time.ZoneId;
import java.util.Random;

@Configuration
public class BeanConfiguration {

    private static String[] timeZoneIDs = ZoneId.getAvailableZoneIds().stream().filter(z -> !z.contains("/")).toArray(size -> new String[size]);
    private static Random random = new Random();

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @RequestScope
    //@Scope(WebApplicationContext.SCOPE_REQUEST)
    public RequestParameters parameters() {
        var tz = timeZoneIDs[random.nextInt(timeZoneIDs.length)];

        var result = RequestParameters.builder()
                .width(600).height(900)
                .timezone(tz)
                .build();
        return result;
    }

    @Bean(name = "paddle")
    public MapLoader<Path> paddle() {
        return new PaddleSourceLoader();
    }

    @Bean(name = "sorie")
    public MapLoader<String> sorie(SROIELoaderParameters parameters) {
        return new SROIELoader(parameters);
    }
}

