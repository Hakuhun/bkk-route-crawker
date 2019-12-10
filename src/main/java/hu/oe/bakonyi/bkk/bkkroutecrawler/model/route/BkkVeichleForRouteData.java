package hu.oe.bakonyi.bkk.bkkroutecrawler.model.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BkkVeichleForRouteData {
    List<VeichleForRouteModel> list;
}
