package hu.oe.bakonyi.bkk.bkkroutecrawler.client;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import feign.Headers;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "routes",
        url = "${bkk.apiUrl}"
)
public interface BkkRouteClient {
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/vehicles-for-route.json"
    )
    public BkkVeichleForRoute getRoute(@RequestParam("key") String key,
                                       @RequestParam("version") int version,
                                       @RequestParam("appVersion") String appVersion,
                                       @RequestParam("includeReferences") String includeReferences,
                                       @RequestParam("routeId") String routeId,
                                       @RequestParam("related") boolean related
                            );

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/trip-details.json"
    )
    public BkkTripDetails getTrip(@RequestParam("key") String key,
                                  @RequestParam("version") int version,
                                  @RequestParam("appVersion") String appVersion,
                                  @RequestParam("includeReferences") String includeReferences,
                                  @RequestParam("tripId") String tripId,
                                  @RequestParam("veichleId") String veichleId,
                                  @RequestParam("related") boolean related
                        );
}
