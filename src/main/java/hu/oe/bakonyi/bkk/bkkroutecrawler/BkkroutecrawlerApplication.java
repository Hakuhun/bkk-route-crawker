package hu.oe.bakonyi.bkk.bkkroutecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BkkroutecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkkroutecrawlerApplication.class, args);
	}

}
