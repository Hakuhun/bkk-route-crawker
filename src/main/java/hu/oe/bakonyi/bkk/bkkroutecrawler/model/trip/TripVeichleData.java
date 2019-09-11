package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import lombok.Data;

import java.util.Date;

@Data
public class TripVeichleData {
    private String tripId;
    private String vehicleId;
    private String stopId;
    private int stopSequence;
    private String routeId;
    private Location location;
    private Date serviceDate;
    private String licensePlate;
    private String label;
    private String model;
    private boolean deviated;
    private long lastUpdateTime;
    private String status;
    private String vehicleRouteType;
    private int stopDistancePercent;
}
