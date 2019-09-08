package hu.oe.bakonyi.bkk.bkkroutecrawler.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "bkk")
public class BkkConfiguration {
    private String apiUrl;
    private String apiKey;
    private String appVersion;
    private int version;
}
