package hu.oe.bakonyi.bkk.bkkroutecrawler.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.model.DonwloaderDataError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class DownloaderDataErrorException extends Exception{

    @Autowired
    ObjectMapper objectMapper;

    DonwloaderDataError errorObject = null;

    public DownloaderDataErrorException(DonwloaderDataError errorObject) {
        this.errorObject = errorObject;
    }

    public DownloaderDataErrorException(String route, String trip, String vehicle) {
        this.errorObject = DonwloaderDataError.builder().routeId(route).tripId(trip).vehicleId(vehicle).build();
    }

    @Override
    public String getMessage() {
        return  "A letoltes soran hiba tortent egy ertek nelkuli valtozo miatt: " + this;
    }

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
