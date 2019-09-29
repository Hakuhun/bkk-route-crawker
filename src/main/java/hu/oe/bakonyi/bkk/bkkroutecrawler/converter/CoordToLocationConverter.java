package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Coord;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class CoordToLocationConverter implements Converter<Coord, Location> {
    @Override
    public Location convert(Coord source) {
        return Location.builder().lon(source.getLon()).lat(source.getLat()).build();
    }
}
