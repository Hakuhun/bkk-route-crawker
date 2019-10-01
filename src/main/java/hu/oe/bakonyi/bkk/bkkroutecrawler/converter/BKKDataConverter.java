package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.TripStopData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BKKDataConverter {

    public BkkData convert(VeichleForRouteModel routeData, BkkTripDetails tripData, TripStopData stopData) throws DownloaderDataErrorException{

        if (StringUtils.isEmpty(tripData.getData().getEntry().getTripId()) || StringUtils.isEmpty(tripData.getData().getEntry().getVehicle().getVehicleId()) || StringUtils.isEmpty(routeData.getRouteId()) || StringUtils.isEmpty(stopData.getStopId())){
            throw new DownloaderDataErrorException(routeData.getRouteId(), tripData.getData().getEntry().getTripId(), tripData.getData().getEntry().getVehicle().getVehicleId(), stopData.getStopId());
        }

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

}
