package hu.oe.bakonyi.bkk.bkkroutecrawler;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableFeignClients
@EnableKafka
@EnableScheduling
@ComponentScan("hu.oe.bakonyi")
public class BkkroutecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkkroutecrawlerApplication.class, args);
	}
}
