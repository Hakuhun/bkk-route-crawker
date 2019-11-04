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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<BkkData> getRouteDataForRoute(String routeId) throws DownloaderDataErrorException{
        List<BkkData> datas = new ArrayList<>();
        List<String> httpStatuses = new ArrayList<>();

        BkkVeichleForRoute routeWrapper = this.getRouteData(routeId);
        httpStatuses.add(routeWrapper.getStatus());

        if(routeWrapper.getData() == null || routeWrapper.getStatus().equals("NOT_FOUND")){
            throw new DownloaderDataErrorException(HttpStatus.NOT_FOUND,"A "+ routeId + " viszonylatazonosítóhoz nem található adat.");
        }

        BkkTripDetails tripData = null;
        for(VeichleForRouteModel routeData : routeWrapper.getData().getList() ){

            tripData = this.getTripData(routeData.getTripId(), routeData.getVehicleId(), routeData.getServiceDate());

           httpStatuses.add(tripData.getStatus());
            if(tripData.getData() != null){
                for(TripStopData stopData : tripData.getData().getEntry().getStopTimes()){
                    boolean alert = getAlert(tripData, routeData.getRouteId(), stopData.getStopId());
                    BkkData detailedStopData = null;
                    try{
                        detailedStopData = converter.convert(routeData, tripData, stopData, httpStatuses, alert);
                        log.info("Új routeData: ".concat(detailedStopData.toString()));
                        datas.add(detailedStopData);
                    }catch (DownloaderDataErrorException ex){
                        log.error(ex.getMessage());
                    }
                }
            }
        }
        return datas;
    }

    boolean getAlert(BkkTripDetails tripData, String route, String stop){
        boolean alert = false;
        if (tripData.getData().getReferences() != null && tripData.getData().getReferences().getAlerts() != null)
            alert = tripData.getData().getReferences().getAlerts().values().stream().anyMatch(x -> x.getRouteIds().contains(route) && x.getStopIds().contains(stop));
        return alert;
    }

    private BkkVeichleForRoute getRouteData(String route){
         return externalBkkRestClient.getRoute(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "alerts",
                route,
                false
        );
    }

    private BkkTripDetails getTripData(String trip, String vehicle, String date){

        return externalBkkRestClient.getTrip(
                configuration.getApiKey(),
                configuration.getVersion(),
                configuration.getAppVersion(),
                "alerts",
                trip,
                vehicle,
                false,
                date
        );
    }
}
