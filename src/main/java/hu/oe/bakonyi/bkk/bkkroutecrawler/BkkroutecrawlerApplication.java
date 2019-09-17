package hu.oe.bakonyi.bkk.bkkroutecrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@EnableFeignClients
@ComponentScan("hu.oe.bakonyi")
public class BkkroutecrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkkroutecrawlerApplication.class, args);
	}

}
