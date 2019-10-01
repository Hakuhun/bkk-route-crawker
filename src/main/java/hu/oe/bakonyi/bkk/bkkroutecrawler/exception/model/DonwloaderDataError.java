package hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonwloaderDataError {
    @Builder.Default
    private String routeId = "";
    @Builder.Default
    private String tripId = "";
    @Builder.Default
    private String vehicleId = "";
    @Builder.Default
    private String stopId = "";
}
