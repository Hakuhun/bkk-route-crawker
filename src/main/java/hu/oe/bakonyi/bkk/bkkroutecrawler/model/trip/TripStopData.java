package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import lombok.Data;

@Data
public class TripStopData {
    private String stopId;
    private String stopHeadsign ;
    private long departureTime;
    private long predictedDepartureTime;
    private long arrivalTime;
    private long predictedArrivalTime;
    private int stopSequence;
}
