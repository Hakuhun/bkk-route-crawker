package hu.oe.bakonyi.bkk.bkkroutecrawler.scheulder;

import hu.oe.bakonyi.bkk.bkkroutecrawler.kafka.KafkaService;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.BkkBusinessDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Log4j2
@Component
public class BkkDataScheulder {

    @Autowired
    BkkBusinessDataService service;

    @Autowired
    KafkaService kafkaService;

    @PostConstruct
    void init(){
        try {
            doWork();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 300000)
    public void bkkDataScheulder(){
        try {
            doWork();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    void doWork() throws Exception {
        List<BkkBusinessData> datas = service.getData();

        datas.forEach(data->{
            kafkaService.sendMessage(data);
        });
    }


}
