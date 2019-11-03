package hu.oe.bakonyi.bkk.bkkroutecrawler.model.route;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.BkkBaseModel;
import lombok.Data;

@Data
public class BkkVeichleForRoute extends BkkBaseModel {

    private BkkVeichleForRouteData data;
    private boolean outOfRange;
    private boolean limitExceeded;

}
