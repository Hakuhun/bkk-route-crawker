package hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk;

import lombok.Data;

@Data
public class BkkData {
    private String routeId;
    private String tripId;
    private String vehicleId;
    private String stopId;
    private int stopSequence;
    private long departureTime;
    private long estimatedDepartureTime;
    private long arrivalTime;
    private long estimatedArrivalTime;
    private long departureDiff;
    private long arrivalDiff;
    private String model;
}
