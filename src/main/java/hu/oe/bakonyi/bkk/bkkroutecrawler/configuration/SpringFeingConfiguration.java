package hu.oe.bakonyi.bkk.bkkroutecrawler.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringFeingConfiguration implements RequestInterceptor {

    private static final String HEADER_USERAGENT = "User-Agent";
    private static final String HEADER_USERAGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";

    private static final String HEADER_COOKIE = "cookie";

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(HEADER_USERAGENT, HEADER_USERAGENT_VALUE);
        template.header(HEADER_COOKIE, "");
    }
}
