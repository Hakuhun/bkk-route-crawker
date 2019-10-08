package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model.DonwloaderDataError;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.TripStopData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BKKDataConverter {

    public BkkData convert(VeichleForRouteModel routeData, BkkTripDetails tripData, TripStopData stopData) throws DownloaderDataErrorException{

        checkValidity(routeData, tripData, stopData);

        return new BkkData(){{
            setRouteId(routeData.getRouteId());
            setStopId(stopData.getStopId());
            setTripId(tripData.getData().getEntry().getTripId());
            setVehicleId(tripData.getData().getEntry().getVehicle().getVehicleId());
            setArrivalTime(stopData.getArrivalTime());
            setEstimatedArrivalTime(stopData.getPredictedArrivalTime());
            setArrivalDiff(Math.abs(stopData.getArrivalTime()-stopData.getPredictedArrivalTime()));
            setDepartureTime(stopData.getDepartureTime());
            setEstimatedDepartureTime(stopData.getDepartureTime());
            setDepartureDiff(Math.abs(stopData.getDepartureTime()-stopData.getPredictedDepartureTime()));
            setLocation(tripData.getData().getEntry().getVehicle().getLocation());
            setModel(tripData.getData().getEntry().getVehicle().getModel());
            setStopSequence(stopData.getStopSequence());
            setLastUpdateTime(tripData.getData().getEntry().getVehicle().getLastUpdateTime());
        }};
    }

    private void checkValidity(VeichleForRouteModel routeData, BkkTripDetails tripData, TripStopData stopData) throws DownloaderDataErrorException {
        DonwloaderDataError.DonwloaderDataErrorBuilder builder = DonwloaderDataError.builder();

        boolean isNullAnyParam = false;

        if (tripData != null && !StringUtils.isEmpty(tripData.getData().getEntry().getTripId())){
            builder.tripId(tripData.getData().getEntry().getTripId());
        }else {
            isNullAnyParam = true;
        }

        if(tripData.getData().getEntry().getVehicle() != null &&  !StringUtils.isEmpty(tripData.getData().getEntry().getVehicle().getVehicleId())){
            builder.vehicleId(tripData.getData().getEntry().getVehicle().getVehicleId());
        }else isNullAnyParam = true;

        if(routeData != null && !StringUtils.isEmpty(routeData.getRouteId())){
            builder.routeId(routeData.getRouteId());
        }else isNullAnyParam = true;

        if(stopData != null && !StringUtils.isEmpty(stopData.getStopId())){
            builder.stopId(stopData.getStopId());
        }else isNullAnyParam = true;

        if(isNullAnyParam){
            throw new DownloaderDataErrorException(builder.build());
        }
    }

}
