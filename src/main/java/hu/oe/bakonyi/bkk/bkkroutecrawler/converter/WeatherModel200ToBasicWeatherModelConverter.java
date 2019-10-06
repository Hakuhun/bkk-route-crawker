package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.BasicWeatherModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import org.springframework.core.convert.converter.Converter;

public class WeatherModel200ToBasicWeatherModelConverter implements Converter<Model200, BasicWeatherModel> {
    @Override
    public BasicWeatherModel convert(Model200 source) {
        BasicWeatherModel wModel = new BasicWeatherModel();

        if(source.getMain().getTemp() != null){
            wModel.setTemperature(source.getMain().getTemp());
        }else{
            wModel.setTemperature(0);
        }
        if(source.getMain().getHumidity() != null){
            wModel.setHumidity(source.getMain().getHumidity());
        }
        if(source.getMain().getPressure() != null){
            wModel.setPressure(source.getMain().getPressure());
        }
        if(source.getRain() != null){
            wModel.setRainIntensity(source.getRain().get_3h());
        }else{
            wModel.setRainIntensity(0);
        }
        if(source.getSnow() != null){
            wModel.setSnowIntensity(source.getSnow().get_3h());
        }else{
            wModel.setSnowIntensity(0);
        }
        if(source.getVisibility() != null){
            wModel.setVisibility(source.getVisibility());
        }

        if (source.getCoord() != null)
            wModel.setLocation(Location.builder().lat(source.getCoord().getLat()).lon(source.getCoord().getLon()).build());

        return wModel;
    }
}
