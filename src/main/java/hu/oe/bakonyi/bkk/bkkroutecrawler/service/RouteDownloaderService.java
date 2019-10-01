package hu.oe.bakonyi.bkk.bkkroutecrawler.service;

import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.configuration.BkkConfiguration;
import hu.oe.bakonyi.bkk.bkkroutecrawler.converter.BKKDataConverter;
import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.TripStopData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.repository.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    BKKDataConverter converter;

    public List<BkkData> getRouteDatas() throws DownloaderDataErrorException {
        List<BkkData> datas = new ArrayList<>();

        for(Routes route : repository.findAll()){
            BkkVeichleForRoute routeWrapper = this.getRouteData(route.getRouteCode());

            if(routeWrapper == null || routeWrapper.getData() == null) break;

            BkkTripDetails tripData = null;
            for(VeichleForRouteModel routeData : routeWrapper.getData().getList() ){

                 tripData = this.getTripData(routeData.getTripId(), routeData.getVehicleId());
                 if(tripData == null || tripData.getData() == null) break;

                 for(TripStopData stopData : tripData.getData().getEntry().getStopTimes()){

                     BkkData detailedStopData = null;

                     try {
                         detailedStopData = converter.convert(routeData, tripData, stopData);
                         log.info("Új routeData: ".concat(detailedStopData.toString()));
                         datas.add(detailedStopData);
                     }catch(DownloaderDataErrorException ddee){
                         log.error(ddee.getMessage());
                     }
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
