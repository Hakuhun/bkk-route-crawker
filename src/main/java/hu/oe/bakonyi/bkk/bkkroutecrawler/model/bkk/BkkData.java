package hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import lombok.Data;

@Data
public class BkkData {
    private Location location;
    private String routeId;
    private String tripId;
    private String vehicleId;
    private String stopId;
    private Long lastUpdateTime;
    private int stopSequence;
    private long departureTime;
    private long estimatedDepartureTime;
    private long arrivalTime;
    private long estimatedArrivalTime;
    private long departureDiff;
    private long arrivalDiff;
    private String model;
}
