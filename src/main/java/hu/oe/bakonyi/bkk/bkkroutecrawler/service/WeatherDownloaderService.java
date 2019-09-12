package hu.oe.bakonyi.bkk.bkkroutecrawler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkWeatherClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather.Model200;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class WeatherDownloaderService {

    @Autowired
    BkkWeatherClient client;

    @Autowired
    ObjectMapper mapper;

    @Value("${weather.fileSystemPath}")
    String path;

    public void downloadWeatherData(){
        File weatherFile = new File(path);
        List<Model200> weathers;
        BasicFileAttributes attributes = null;
        try {
            attributes = Files.readAttributes(weatherFile.toPath(),BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            log.error("A hoszton nincs még időjárás adat.");
        }

        if (attributes != null){
            weathers = client.getWeather(String.valueOf(attributes.creationTime().toInstant().getEpochSecond()));
        }else{
            weathers = client.getWeather(String.valueOf("0"));
        }

        try {
            mapper.writeValue(new File(path), weathers);
            log.info("Időjárási adatok frissítve.");
        } catch (IOException e) {
            log.error("Hiba történt az időjárás adatok mentése során.");
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
    }

    public List<Model200> getWeatherData() throws IOException {
        return Arrays.asList(mapper.readValue(new File(path), Model200[].class));
    }

}
