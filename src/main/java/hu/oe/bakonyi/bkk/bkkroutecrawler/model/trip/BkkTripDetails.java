package hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.BkkBaseModel;
import lombok.Data;

@Data
public class BkkTripDetails extends BkkBaseModel {

    private BkkTripDetailsData data;

}
