package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import lombok.Data;

@Data
public class BkkTripDetailsData {
    private boolean limitExceeded;
    private BkkTripDetailsDataEntry entry;

}
