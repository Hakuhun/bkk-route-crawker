package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.BkkBaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkkTripDetails extends BkkBaseModel {
    private BkkTripDetailsData data;
}
