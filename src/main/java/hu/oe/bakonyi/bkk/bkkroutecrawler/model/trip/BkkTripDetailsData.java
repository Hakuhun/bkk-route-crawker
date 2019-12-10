package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkReferences;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkkTripDetailsData {
    private boolean limitExceeded;
    private BkkTripDetailsDataEntry entry;
    private BkkReferences references;

}
