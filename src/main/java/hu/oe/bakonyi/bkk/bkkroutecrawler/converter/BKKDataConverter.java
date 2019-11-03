package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model.DonwloaderDataError;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.TripStopData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.RouteDownloaderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Log4j2
public class BKKDataConverter {

    public BkkData convert(VeichleForRouteModel routeData, BkkTripDetails tripData, TripStopData stopData, List<String> statuses, boolean alert) throws DownloaderDataErrorException{

        checkValidity(routeData, tripData, stopData, statuses);

        log.info("ROUTE: " + routeData.getRouteId() + " TRIP : " + routeData.getTripId());
        log.info("Érkezés: " +stopData.getArrivalTime() + " - " + stopData.getPredictedArrivalTime() + " = " +  Math.abs(stopData.getArrivalTime()-stopData.getPredictedArrivalTime()));
        log.info("Távozás: " +stopData.getDepartureTime() + " - " + stopData.getPredictedDepartureTime() + " = " +  Math.abs(stopData.getDepartureTime()-stopData.getPredictedDepartureTime()));

        return new BkkData(){{
            setRouteId(routeData.getRouteId());
            setStopId(stopData.getStopId());
            setTripId(tripData.getData().getEntry().getTripId());
            //setVehicleId(tripData.getData().getEntry().getVehicle().getVehicleId());
            setVehicleId(routeData.getVehicleId());
            setArrivalTime(stopData.getArrivalTime());
            setEstimatedArrivalTime(stopData.getPredictedArrivalTime());
            setArrivalDiff(Math.abs(stopData.getArrivalTime()-stopData.getPredictedArrivalTime()));
            setDepartureTime(stopData.getDepartureTime());
            setEstimatedDepartureTime(stopData.getDepartureTime());
            setDepartureDiff(Math.abs(stopData.getDepartureTime()-stopData.getPredictedDepartureTime()));
            setLocation(routeData.getLocation());
            setModel(routeData.getModel());
            setStopSequence(stopData.getStopSequence());
            setLastUpdateTime(routeData.getLastUpdateTime());
            setAlert(alert);
        }};
    }

    private void checkValidity(VeichleForRouteModel routeData, BkkTripDetails tripData, TripStopData stopData, List<String> status) throws DownloaderDataErrorException {
        DonwloaderDataError.DonwloaderDataErrorBuilder builder = DonwloaderDataError.builder();

        boolean isNullAnyParam = false;

        if(status.contains("NOT_FOUND")){
            throw new DownloaderDataErrorException(HttpStatus.NOT_FOUND ,builder.build());
        }

        if(status.contains("NOT_OPERATING")){
            throw new DownloaderDataErrorException(HttpStatus.NO_CONTENT ,builder.build());
        }

        if (tripData != null && !StringUtils.isEmpty(tripData.getData().getEntry().getTripId())){
            builder.tripId(tripData.getData().getEntry().getTripId());
        }else {
            isNullAnyParam = true;
        }

        if(!StringUtils.isEmpty(routeData.getVehicleId())){
            builder.vehicleId(routeData.getVehicleId());
        }else isNullAnyParam = true;

        if(!StringUtils.isEmpty(routeData.getRouteId())){
            builder.routeId(routeData.getRouteId());
        }else isNullAnyParam = true;

        if(stopData != null && !StringUtils.isEmpty(stopData.getStopId())){
            builder.stopId(stopData.getStopId());
        }else isNullAnyParam = true;

        if(isNullAnyParam){
            throw new DownloaderDataErrorException(HttpStatus.NO_CONTENT, builder.build());
        }
    }

}
