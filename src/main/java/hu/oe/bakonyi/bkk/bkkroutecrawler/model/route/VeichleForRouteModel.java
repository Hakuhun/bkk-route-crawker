package hu.oe.bakonyi.bkk.bkkroutecrawler.model.route;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Capacity;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import lombok.Data;

@Data
public class VeichleForRouteModel {
    private String vehicleId;
    private String stopId;
    private int stopSequence;
    private String routeId;
    private int bearing;
    private Location location;
    private String serviceDate;
    private String licensePlate;
    private String label;
    private String model;
    private boolean deviated;
    private long lastUpdateTime;
    private String status;
    private double congestionLevel;
    private String vehicleRouteType;
    private int stopDistancePercent;
    private Capacity capacity;
    private String tripId;



}
