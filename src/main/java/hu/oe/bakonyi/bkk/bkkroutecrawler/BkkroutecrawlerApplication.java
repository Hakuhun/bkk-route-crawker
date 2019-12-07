package hu.oe.bakonyi.bkk.bkkroutecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableFeignClients
@EnableScheduling
@ComponentScan("hu.oe.bakonyi")
public class BkkroutecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkkroutecrawlerApplication.class, args);
	}
}
