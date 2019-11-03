package hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BkkAlertDetails {
    private HashMap<String, BkkAlertData> alertReferences;
}
