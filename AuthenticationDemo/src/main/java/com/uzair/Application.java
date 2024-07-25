package com.uzair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling            //enable the automatic calling of the scheduled method on the regular interval.
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
