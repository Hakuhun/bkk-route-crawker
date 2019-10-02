package hu.oe.bakonyi.bkk.bkkroutecrawler.kafka;

import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaService {
    private static final String TOPIC = "users";

    @Autowired
    private KafkaTemplate<String, BkkBusinessData> kafkaTemplate;

    public void sendMessage(BkkBusinessData message){
        log.info(String.format("$$ -> Producing message --> %s",message));
        this.kafkaTemplate.send(TOPIC,message);
    }

}
