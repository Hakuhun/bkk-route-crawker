package hu.oe.bakonyi.bkk.bkkroutecrawler.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkBusinessData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class KafkaService {
    private static final String TOPIC = "bkk";

    @Autowired
    private KafkaTemplate<String, BkkBusinessData> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void sendMessage(BkkBusinessData msg){
        log.info(String.format("$$ -> Producing message --> %s",msg));
/*        Message<BkkBusinessData> message = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .build();*/
        this.kafkaTemplate.send(TOPIC, UUID.randomUUID().toString(), msg);
    }

}
