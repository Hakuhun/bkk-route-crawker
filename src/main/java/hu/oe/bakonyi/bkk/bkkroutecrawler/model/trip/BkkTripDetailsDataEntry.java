package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BkkTripDetailsDataEntry {
    private String tripId;
    private String serviceDate;

    private List<TripStopData> stopTimes;
}
