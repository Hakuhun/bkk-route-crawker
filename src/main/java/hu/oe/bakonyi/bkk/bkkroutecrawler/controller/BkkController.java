package hu.oe.bakonyi.bkk.bkkroutecrawler.controller;

import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.BkkConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.BkkVeichleForRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("route")
public class BkkController {

    @Autowired
    BkkRouteClient routeClient;

    @Autowired
    BkkConfiguration configuration;

    @GetMapping("route")
    public ResponseEntity<BkkVeichleForRoute> getRoute(@RequestParam("route") String route){
        routeClient.getRoute(configuration.getApiKey(), configuration.getVersion(), configuration.getAppVersion(), "false", route, false);
        //return ResponseEntity.ok(routeData);
        return null;
    }
}
