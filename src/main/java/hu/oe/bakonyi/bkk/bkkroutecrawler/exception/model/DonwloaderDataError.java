package hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model;

import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
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

    //public static DonwloaderDataErrorBuilder getBuilder (){return new DonwloaderDataErrorBuilder();}
}
