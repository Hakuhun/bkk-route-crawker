package hu.oe.bakonyi.bkk.bkkroutecrawler.service;

import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.BkkConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.TripStopData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.repository.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RouteDownloaderService {

    @Autowired
    RouteRepository repository;

    @Autowired
    BkkRouteClient externalBkkRestClient;

    @Autowired
    BkkConfiguration configuration;

    public List<BkkData> getRouteDatas() {
        List<BkkData> datas = new ArrayList<>();

        for(Routes route : repository.findAll()){
            BkkVeichleForRoute routeWrapper = this.getRouteData(route.getRouteCode());
            BkkTripDetails tripData = null;
            for(VeichleForRouteModel routeData : routeWrapper.getData().getList() ){
                 tripData = this.getTripData(routeData.getTripId(), routeData.getVehicleId());
                 for(TripStopData stopData : tripData.getData().getEntry().getStopTimes()){
                     BkkTripDetails finalTripData = tripData;
                     BkkData detailedStopData = new BkkData(){{
                         setRouteId(routeData.getRouteId());
                         setStopId(stopData.getStopId());
                         setTripId(finalTripData.getData().getEntry().getTripId());
                         setVehicleId(finalTripData.getData().getEntry().getVehicle().getVehicleId());
                         setArrivalTime(stopData.getArrivalTime());
                         setEstimatedArrivalTime(stopData.getPredictedArrivalTime());
                         setArrivalDiff(Math.abs(stopData.getArrivalTime()-stopData.getPredictedArrivalTime()));
                         setDepartureTime(stopData.getDepartureTime());
                         setEstimatedDepartureTime(stopData.getDepartureTime());
                         setDepartureDiff(Math.abs(stopData.getDepartureTime()-stopData.getPredictedDepartureTime()));
                         setLocation(finalTripData.getData().getEntry().getVehicle().getLocation());
                         setModel(finalTripData.getData().getEntry().getVehicle().getModel());
                         setStopSequence(stopData.getStopSequence());
                         setLastUpdateTime(finalTripData.getData().getEntry().getVehicle().getLastUpdateTime());
                     }};
                     log.info("Új routeData: ".concat(detailedStopData.toString()));
                     datas.add(detailedStopData);
                 }
            }
        }
        return datas;
    }

    private BkkVeichleForRoute getRouteData(String route){
        return externalBkkRestClient.getRoute(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "false",
                route,
                false
        );
    }

    private BkkTripDetails getTripData(String trip, String veichle){
        return externalBkkRestClient.getTrip(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "false",
                trip,
                veichle,
                false
        );
    }
}
