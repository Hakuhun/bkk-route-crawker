package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BkkTripDetailsDataEntry {
    private String tripId;
    private String serviceDate;
    @JsonProperty("alertIds")
    private String[] alertIds;
    private List<TripStopData> stopTimes;
}
