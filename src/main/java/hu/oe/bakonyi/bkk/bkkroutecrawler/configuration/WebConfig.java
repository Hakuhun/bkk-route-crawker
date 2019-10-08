package hu.oe.bakonyi.bkk.bkkroutecrawler.configuration;

import hu.oe.bakonyi.bkk.bkkroutecrawler.converter.CoordToLocationConverter;
import hu.oe.bakonyi.bkk.bkkroutecrawler.converter.WeatherModel200ToBasicWeatherModelConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CoordToLocationConverter());
    }
}