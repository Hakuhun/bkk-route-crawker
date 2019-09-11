package hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.BasicWeatherModel;
import lombok.Data;

@Data
public class BkkBusinessData {
    private Location location;
    private BkkData bkk;
    private BasicWeatherModel weather;
    private long currentTime;
    private int month;
    private int dayOfTheWeek;
}
