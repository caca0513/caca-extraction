package caca.extraction;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

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
                .height(600)
                .width(800)
                .timezone(tz)
                .build();
        return result;
    }
}
