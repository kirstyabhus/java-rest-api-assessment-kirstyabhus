package com.cbfacademy.apiassessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbfacademy.apiassessment.utility.JsonUtility;
import com.cbfacademy.apiassessment.utility.PortfolioCreator;
import com.cbfacademy.apiassessment.model.Portfolio;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class App {

	public static void main(String[] args) {
		// SpringApplication.run(App.class, args);

		JsonUtility jsonUtility = new JsonUtility();

		String filePath = "src//main//resources//test.json";

		List<Portfolio> result = jsonUtility.readPortfoliosFromJSON(filePath);

		System.out.println(result.toString());

		// create a new portfolio with investments
		// Portfolio trendingStocks =

		/*
		 * PortfolioCreator portfolioCreator = new PortfolioCreator();
		 * 
		 * Portfolio portfolio1 = portfolioCreator.createHealthcareETFsPortfolio();
		 * Portfolio portfolio2 = portfolioCreator.createTechStocksPortfolio();
		 * 
		 * List<Portfolio> newPortfolio = new ArrayList<>();
		 * newPortfolio.add(portfolio1);
		 * newPortfolio.add(portfolio2);
		 * 
		 * // System.out.println(newPortfolio);
		 * 
		 * String filePath = "src//main//resources//test.json";
		 * 
		 * JsonUtility jsonUtility = new JsonUtility();
		 * jsonUtility.writePortfoliosToJSON(newPortfolio, filePath);
		 * 
		 */
	}

	/*
	 * @GetMapping("/api/portfolios")
	 * public String greeting(@RequestParam(value = "name", defaultValue = "World")
	 * String name) {
	 * return String.format("Hello %s", name);
	 * }
	 */

}
