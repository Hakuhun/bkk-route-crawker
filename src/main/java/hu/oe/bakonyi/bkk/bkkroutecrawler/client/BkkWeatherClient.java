package hu.oe.bakonyi.bkk.bkkroutecrawler.client;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Coord;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "weather",
        url = "${weather.url}"
)
public interface BkkWeatherClient {
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/weathers"
    )
    public List<Model200> getWeather(@RequestParam("time") String time);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/weather"
    )
    Model200 getWeatherByCoord(@RequestBody Coord coord);
}

