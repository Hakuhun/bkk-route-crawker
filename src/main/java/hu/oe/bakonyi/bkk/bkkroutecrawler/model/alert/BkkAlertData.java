package hu.oe.bakonyi.bkk.bkkroutecrawler.model.alert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BkkAlertData {
    @JsonProperty("id")
    private String id;
    @JsonProperty("stopIds")
    private List<String> stopIds;
    @JsonProperty("routeIds")
    private List<String> routeIds;
}
