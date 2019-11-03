package hu.oe.bakonyi.bkk.bkkroutecrawler.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model.DonwloaderDataError;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
public class DownloaderDataErrorException extends ResponseStatusException {
    DonwloaderDataError errorObject = null;

    public DownloaderDataErrorException(HttpStatus status, String message) {
        super(status, message);
        this.errorObject = DonwloaderDataError.builder().routeId("").tripId("").vehicleId("").stopId("").build();
    }

    public DownloaderDataErrorException(HttpStatus status, DonwloaderDataError errorObject) {
        super(status);
        this.errorObject = errorObject;
    }

    public DownloaderDataErrorException(HttpStatus status, String route, String trip, String vehicle, String stop) {
        super(status);
        this.errorObject = DonwloaderDataError.builder().routeId(route).tripId(trip).vehicleId(vehicle).stopId(stop).build();
    }

    @Override
    public String getMessage() {
        return  super.getMessage() + System.lineSeparator() + "A letöltéshez szüséges adatok hiányosak: " + this.toString();
    }

}
