package hu.oe.bakonyi.bkk.bkkroutecrawler.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private double lat;
    private double lon;
}
