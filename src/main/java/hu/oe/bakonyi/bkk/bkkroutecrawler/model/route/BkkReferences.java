package hu.oe.bakonyi.bkk.bkkroutecrawler.model.route;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert.BkkAlertData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert.BkkAlertDetails;
import lombok.Data;

import java.util.HashMap;

@Data
public class BkkReferences {
    private HashMap<String, BkkAlertData> alerts;
}
