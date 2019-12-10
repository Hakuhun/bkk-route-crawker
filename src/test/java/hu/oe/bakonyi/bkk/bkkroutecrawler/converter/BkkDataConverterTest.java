package hu.oe.bakonyi.bkk.bkkroutecrawler.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.exception.DownloaderDataErrorException;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.Location;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.VeichleForRouteModel;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BkkDataConverterTest {

    @Test
    public void test_convert_convertsToBkkData_With_CorrectData() throws IOException {
        BKKDataConverter converter = new BKKDataConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        BkkVeichleForRoute routeModel = mapper.readValue(ResourceUtils.getFile("classpath:VeichlesForRoutes_ok.json"), BkkVeichleForRoute.class);
        BkkTripDetails tripDetails = mapper.readValue(ResourceUtils.getFile("classpath:TrpDetails_ok.json"), BkkTripDetails.class);

        BkkData data = converter.convert(routeModel.getData().getList().get(1),tripDetails,tripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("OK"),false);
        Assert.assertNotNull(data);
        Assert.assertEquals(data.getLocation(), Location.builder().lat(47.457565).lon(19.028755).build());
        Assert.assertEquals(data.getModel(),"MERCEDES Conecto csuklós");
        Assert.assertEquals(data.getArrivalDiff(),13);
        Assert.assertEquals(data.getDepartureDiff(), 13);
    }

    @Test
    public void test_convert_throwsException_With_MissingOrIncorrectData() throws IOException {
        BKKDataConverter converter = new BKKDataConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        BkkVeichleForRoute routeModel = mapper.readValue(ResourceUtils.getFile("classpath:VeichlesForRoutes_ok.json"), BkkVeichleForRoute.class);
        BkkTripDetails tripDetails = mapper.readValue(ResourceUtils.getFile("classpath:TrpDetails_ok.json"), BkkTripDetails.class);

        assertThrows(DownloaderDataErrorException.class, () ->converter.convert(routeModel.getData().getList().get(1),tripDetails,tripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("NOT_FOUND"),false));
        assertThrows(DownloaderDataErrorException.class, () ->converter.convert(routeModel.getData().getList().get(1),tripDetails,tripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("NOT_OPERATING"),false));
        assertThrows(DownloaderDataErrorException.class, () ->converter.convert(routeModel.getData().getList().get(1),null,tripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("OK"),false));

        BkkTripDetails missingTripIdTripDetails = mapper.readValue(ResourceUtils.getFile("classpath:TripDetails_nok_MissingTripId.json"), BkkTripDetails.class);
        assertThrows(DownloaderDataErrorException.class, () ->converter.convert(routeModel.getData().getList().get(1),missingTripIdTripDetails,missingTripIdTripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("OK"),false));

        BkkTripDetails missingStopIdTripDetails = mapper.readValue(ResourceUtils.getFile("classpath:TripDetails_nok_MissingStopId.json"), BkkTripDetails.class);
        assertThrows(DownloaderDataErrorException.class, () ->converter.convert(routeModel.getData().getList().get(1),missingStopIdTripDetails,missingStopIdTripDetails.getData().getEntry().getStopTimes().get(1), Arrays.asList("OK"),false));
    }

}
