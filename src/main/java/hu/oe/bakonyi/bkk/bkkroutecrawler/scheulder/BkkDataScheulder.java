package hu.oe.bakonyi.bkk.bkkroutecrawler.scheulder;

import hu.oe.bakonyi.bkk.bkkroutecrawler.kafka.KafkaService;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkroutecrawler.service.BkkBusinessDataService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "${scheulder.bkkkScheulder}")
    public void bkkDataScheulder(){
      try {
            doWork();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    void doWork() throws Exception {
        List<BkkBusinessDataV2> datas = getTableFormat(service.getData());

        for (BkkBusinessDataV2 data : datas) {
            try {
                kafkaService.sendMessage(data);
                log.info("Kafka üzenet elküldve: " + data);
            } catch (KafkaException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    List<BkkBusinessDataV2> getTableFormat(List<BkkBusinessData> data){
        List<BkkBusinessDataV2> v2 = data.stream().map(BkkBusinessDataV2::new).collect(Collectors.toList());
        return v2;
    }

}
