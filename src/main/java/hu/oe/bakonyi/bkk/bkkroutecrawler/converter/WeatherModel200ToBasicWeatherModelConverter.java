package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.BasicWeatherModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import org.springframework.core.convert.converter.Converter;

public class WeatherModel200ToBasicWeatherModelConverter implements Converter<Model200, BasicWeatherModel> {
    @Override
    public BasicWeatherModel convert(Model200 source) {
        return new BasicWeatherModel(){{
            setTemperature(source.getMain().getTemp());
            setHumidity(source.getMain().getHumidity());
            setPressure(source.getMain().getPressure());
            setRainIntensity(source.getRain().get_3h());
            setSnowIntensity(source.getSnow().get_3h());
            setVisibility(source.getVisibility());
            setLocation(Location.builder().lat(source.getCoord().getLat()).lon(source.getCoord().getLon()).build());
        }};
    }
}
