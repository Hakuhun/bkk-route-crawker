package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Coord;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class CoordToLocationConverter {
    public Location toLocation(Coord source) {
        return Location.builder().lon(source.getLon()).lat(source.getLat()).build();
    }

    public Coord toCoord(Location location){
        return Coord.builder().lat(location.getLat()).lon(location.getLon()).build();
    }
}
