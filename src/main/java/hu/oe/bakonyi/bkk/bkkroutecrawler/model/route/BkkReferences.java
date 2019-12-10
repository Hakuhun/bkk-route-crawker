package hu.oe.bakonyi.bkk.bkkroutecrawler.model.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert.BkkAlertData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert.BkkAlertDetails;
import lombok.Data;

import java.util.HashMap;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkkReferences {
    private HashMap<String, BkkAlertData> alerts;
}
