package hu.oe.bakonyi.bkk.bkkroutecrawler.client;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.BkkVeichleForRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "routes",
        url = "${bkk.apiUrl}"
)
public interface BkkRouteClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/valami"
    )
    public void getRoute(@RequestParam("key")String key,
                                       @RequestParam("version") int version,
                                       @RequestParam("appVersion") String appVersion,
                                       @RequestParam("includeReferences") String includeReferences,
                                       @RequestParam("routeId") String routeId,
                                       @RequestParam("related") boolean related
                         );


}
