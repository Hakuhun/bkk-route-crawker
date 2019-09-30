package hu.oe.bakonyi.bkk.bkkroutecrawler.service;

import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.BasicWeatherModel;
import lombok.extern.log4j.Log4j2;
import net.sf.geographiclib.Geodesic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class BkkBusinessDataService {

    @Autowired
    RouteDownloaderService routeService;

    @Autowired
    WeatherDownloaderService weatherService;

    @Autowired
    ConversionService conversionService;

    public List<BkkBusinessData> getData() throws Exception {
        List<BkkData> routeDatas = routeService.getRouteDatas();
        List<BasicWeatherModel> weatherModels = weatherService.getWeatherData();
        List<BkkBusinessData> businessDatas = new ArrayList<>();

        BkkBusinessData businessData = null;

        for (BkkData route : routeDatas){
            Instant lastUpdate = Instant.ofEpochSecond(route.getLastUpdateTime());
            businessData = new BkkBusinessData(){{
                setBkk(route);
                setCurrentTime(lastUpdate.getEpochSecond());
                setDayOfTheWeek(lastUpdate.get(ChronoField.DAY_OF_WEEK));
                setMonth(lastUpdate.get(ChronoField.MONTH_OF_YEAR));
                setLocation(route.getLocation());
                setWeather(minimalDistance(route.getLocation(), weatherModels));
            }};
            businessDatas.add(businessData);
        }
        return businessDatas;
    }

    private double getDistance(Location a, Location b){
        return this.getDistance(a.getLat(), a.getLon(), b.getLat(), b.getLon());
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        Geodesic geod = Geodesic.WGS84;// This matches EPSG4326, which is the coordinate system used by Geolake
        return geod.InverseLine(lat1, lon1, lat2, lon2).Distance();
    }

    private BasicWeatherModel minimalDistance(Location veichleLocation, List<BasicWeatherModel>  weatherModels) {
        Map<Double, BasicWeatherModel> distances = new HashMap<>();

        for(BasicWeatherModel weather : weatherModels)
        {
            distances.put(getDistance(weather.getLocation(), veichleLocation), weather);
        }

        double min = Integer.MAX_VALUE;

        for (Map.Entry<Double, BasicWeatherModel> entry : distances.entrySet()) {
            Double key = entry.getKey();
            BasicWeatherModel value = entry.getValue();

            if (min > key)
            {
                min = key;
            }
        }

        return distances.get(min);
    }

}
