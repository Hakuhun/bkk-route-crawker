package hu.oe.bakonyi.bkk.bkkroutecrawler.scheulder;

import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.WeatherConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.RouteDownloaderService;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.WeatherDownloaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Log4j2
@Component
public class BkkDataScheulder {

    @Autowired
    WeatherDownloaderService wService;

    @Autowired
    WeatherConfiguration configuration;

    @Autowired
    RouteDownloaderService routeService;

    @Autowired
    ConversionService conversionService;

    @Scheduled(fixedRate = 300000)
    public void doWork(){
        try {
            List<Model200> weatherList = wService.getWeatherData();
        } catch (IOException e) {
            log.error("Hiba történt az időjárási adatok letöltése/bettöltése közben.");
        }
        List<BkkData> routes = routeService.getRouteDatas();

    }


}
