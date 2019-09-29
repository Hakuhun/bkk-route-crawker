package hu.oe.bakonyi.bkk.bkkroutecrawler.controller;

import feign.FeignException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.repository.RouteRepository;
import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.BkkConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.WeatherDownloaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Log4j2
@RestController("route")
public class BkkController {

    @Autowired
    BkkRouteClient externalBkkRestClient;

    @Autowired
    BkkConfiguration configuration;

    @Autowired
    WeatherDownloaderService service;

    @Autowired
    RouteRepository repository;

    @GetMapping("route")
    public ResponseEntity<BkkVeichleForRoute> getRoute(@RequestParam("route") String route){
        try{
            BkkVeichleForRoute routeData = externalBkkRestClient.getRoute(configuration.getApiKey(), configuration.getVersion(), configuration.getAppVersion(), "false", route, false);
            return ResponseEntity.ok(routeData);
        }catch(FeignException fe){
            log.error(fe.getMessage());
            fe.printStackTrace();
        }
        return null;
    }

    @GetMapping("trip")
    public ResponseEntity<BkkTripDetails> getTrip(@RequestParam("vehicle") String veichle, @RequestParam("trip") String trip){
        BkkTripDetails tripData = externalBkkRestClient.getTrip(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "false",
                trip,
                veichle,
                false
        );

        return ResponseEntity.ok(tripData);
    }

    @GetMapping("download")
    public void asd(){
        try {
            service.getWeatherData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(repository.findAll());
    }

}
