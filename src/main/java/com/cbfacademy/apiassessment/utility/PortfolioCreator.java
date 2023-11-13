package com.cbfacademy.apiassessment.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.model.Stock;

public class PortfolioCreator {

    public Portfolio createTechStocksPortfolio() {
        Portfolio techStocks = new Portfolio();
        techStocks.setName("Tech Stocks");
        techStocks.setPortfolioId();

        // Create and add Stock investments
        List<Investment> techStockInvestments = createTechStockInvestments();
        techStocks.setInvestments(techStockInvestments);

        return techStocks;
    }

    public Portfolio createHealthcareETFsPortfolio() {
        Portfolio healthcareETFs = new Portfolio();
        healthcareETFs.setName("Random ETFs");
        healthcareETFs.setPortfolioId();

        // Create and add ETF investments
        List<Investment> healthcareETFInvestments = createHealthcareETFInvestments();
        healthcareETFs.setInvestments(healthcareETFInvestments);

        return healthcareETFs;
    }

    private static List<Investment> createTechStockInvestments() {
        List<Investment> techStockInvestments = new ArrayList<>();

        Stock appleStock = new Stock("MSFT", "Microsoft Corp", 150.25);
        Stock alphabetStock = new Stock("NVDA", "NVIDIA Corp", 486.20);

        techStockInvestments.add(appleStock);
        techStockInvestments.add(alphabetStock);

        return techStockInvestments;
    }

    private static List<Investment> createHealthcareETFInvestments() {
        List<Investment> healthcareETFInvestments = new ArrayList<>();

        ETF vooETF = new ETF("VOO", "Vanguard S&P 500 ETF", 120.75);
        ETF ijrETF = new ETF("IJR", "iShares Core SP Small-Cap ETF.", 180.40);

        healthcareETFInvestments.add(vooETF);
        healthcareETFInvestments.add(ijrETF);

        return healthcareETFInvestments;
    }
}
