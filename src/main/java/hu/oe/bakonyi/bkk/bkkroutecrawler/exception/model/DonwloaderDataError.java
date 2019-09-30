package hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonwloaderDataError {
    private String routeId;
    private String tripId;
    private String vehicleId;
}
