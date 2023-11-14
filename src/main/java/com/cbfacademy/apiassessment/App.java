package com.cbfacademy.apiassessment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cbfacademy.apiassessment.model.Stock;
import com.cbfacademy.apiassessment.repository.StockRepository;

@SpringBootApplication
public class App {

	// setting up a logger to get ouput to the console (for this example)
	// logs messages
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	// setting up data and using it to generate output
	@Bean
	// this command line runner bean automatically runs the code when the
	// application launches
	public CommandLineRunner demo(StockRepository repository) {
		return (args) -> {
			// first StockRepository is fetched from Spring Application context

			// saves stock objects using the StockRepository.
			// instances of the stocks are created and saved to the database
			repository.save(new Stock("GOOGL", "Alphabet Inc Class A", 135.04));
			repository.save(new Stock("MSFT", "Microsoft Corp", 371.06));
			repository.save(new Stock("NVDA", "NVIDIA Corp", 491.04));
			repository.save(new Stock("TSLA", "Tesla Inc", 232.53));
			repository.save(new Stock("META", "Meta Platforms Inc", 335.62));

			// after saving the stocks
			// FETCHES ALL STOCK objects from database and logs each stock's details using
			// log.info()
			log.info("Stocks found with findAll():");
			log.info("----------------------------");
			repository.findAll().forEach(stock -> {
				log.info(stock.toString());
			});
			log.info("");

			// FETCH STOCK BY ID
			Stock stock = repository.findById(1L);
			log.info("Stock found with findById(1L):");
			log.info("----------------------------------");
			log.info(stock.toString());
			log.info("");

			// FETCH STOCK BY SYMBOL
			log.info("Stock found with findBySymbol('NVDA'):");
			log.info("--------------------------------------");
			repository.findBySymbol("NVDA").forEach(nvda -> {
				log.info(nvda.toString());
			});
			log.info("");
		};

	}
}
