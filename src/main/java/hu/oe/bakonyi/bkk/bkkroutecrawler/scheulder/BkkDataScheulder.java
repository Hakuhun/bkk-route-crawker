package hu.oe.bakonyi.bkk.bkkroutecrawler.scheulder;

import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.WeatherConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.converter.CoordToLocationConverter;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.RouteService;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.WeatherDownloaderService;
import lombok.extern.log4j.Log4j2;
import net.sf.geographiclib.Geodesic;
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
    RouteService routeService;

    @Autowired
    ConversionService conversionService;

    @Scheduled(fixedRate = 300000)
    public void doWork(){
        try {
            List<Model200> weatherList = wService.getWeatherData();
        } catch (IOException e) {
            log.error("Hiba történt az időjárási adatok letöltése/bettöltése közben.");
        }
        //List<BkkData> routes = routeService.getRouteDatas();

    }

    private double getDistance(Location a, Location b){
        return this.getDistance(a.getLat(), a.getLon(), b.getLat(), b.getLon());
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        Geodesic geod = Geodesic.WGS84;// This matches EPSG4326, which is the coordinate system used by Geolake
        return geod.InverseLine(lat1, lon1, lat2, lon2).Distance();
    }


    private Model200 minimalDistance(Location veichleLocation) throws Exception {
        Map<Double, Model200> distances = new HashMap<>();

        for(Model200 weather : wService.getWeatherData())
        {
            distances.put(getDistance(Objects.requireNonNull(conversionService.convert(weather.getCoord(), Location.class)), veichleLocation), weather);
        }

        double min = Integer.MAX_VALUE;

        for (Map.Entry<Double, Model200> entry : distances.entrySet()) {
            Double key = entry.getKey();
            Model200 value = entry.getValue();

            if (min > key)
            {
                min = key;
            }
        }

        return distances.get(min);
    }
}
