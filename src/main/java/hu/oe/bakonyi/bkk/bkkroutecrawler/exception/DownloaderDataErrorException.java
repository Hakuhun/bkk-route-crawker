package hu.oe.bakonyi.bkk.bkkroutecrawler.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model.DonwloaderDataError;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class DownloaderDataErrorException extends Exception{
    DonwloaderDataError errorObject = null;

    public DownloaderDataErrorException(DonwloaderDataError errorObject) {
        this.errorObject = errorObject;
    }

    public DownloaderDataErrorException(String route, String trip, String vehicle, String stop) {
        this.errorObject = DonwloaderDataError.builder().routeId(route).tripId(trip).vehicleId(vehicle).stopId(stop).build();
    }

    @Override
    public String getMessage() {
        return  "A letöltéshez szüséges adatok hiányosak: " + this.toString();
    }

}
