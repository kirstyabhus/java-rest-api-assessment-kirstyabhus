package com.cbfacademy.apiassessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.repository.StockRepository;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class NewApp {

	// setting up a logger to get ouput to the console (for this example)
	// logs messages
	// private static final Logger log = LoggerFactory.getLogger(NewApp.class);

	public static void main(String[] args) {
		SpringApplication.run(NewApp.class, args);
	}

}
