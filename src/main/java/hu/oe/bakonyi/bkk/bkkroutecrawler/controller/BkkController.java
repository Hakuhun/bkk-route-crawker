package hu.oe.bakonyi.bkk.bkkroutecrawler.controller;

import feign.FeignException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.BkkConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.repository.RouteRepository;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.RouteDownloaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Log4j2
@RestController("route")
public class BkkController {
    @Autowired
    BkkRouteClient externalBkkRestClient;

    @Autowired
    BkkConfiguration configuration;

    @Autowired
    RouteDownloaderService bkkService;

    @Autowired
    RouteRepository repository;

    @GetMapping("dev/route")
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

    @GetMapping("dev/trip")
    public ResponseEntity<BkkTripDetails> getTrip(@RequestParam("vehicle") String veichle,
                                                  @RequestParam("trip") String trip,
                                                  @RequestParam("date") String date
    ){
        if(date.isEmpty()){
            Instant instant = Instant.now();
            LocalDate time = instant.atZone(ZoneOffset.UTC).toLocalDate();
            date = time.getYear() + time.getMonthValue() + time.getDayOfMonth()+"";
        }

        BkkTripDetails tripData = externalBkkRestClient.getTrip(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "alerts",
                trip,
                veichle,
                false,
                date
        );

        return ResponseEntity.ok(tripData);
    }

    @GetMapping("prod/route")
    public ResponseEntity<List<BkkData>> getRouteTripData(@RequestParam("route") String route){
        return ResponseEntity.ok(bkkService.getRouteDataForRoute(route));
    }

    @PostMapping("dev/route")
    @Transactional
    public ResponseEntity<Routes> addNewRoute(@RequestBody Routes routes){
        List<Routes> storedRoutes = repository.findRoutesByRouteCode(routes.getRouteCode());
        Routes saved = null;

        if(storedRoutes.isEmpty()){
            saved = repository.save(routes);
        }else{
            repository.deleteAll(storedRoutes);
            saved = repository.save(routes);
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping("prod/routes")
    public ResponseEntity<Iterable<Routes>> getAllStoredRoutes(){
        return ResponseEntity.ok(repository.findAll());
    }

}
