package com.cbfacademy.apiassessment.utility;

import java.util.ArrayList;
import java.util.List;

import com.cbfacademy.apiassessment.model.ETF;
import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import com.cbfacademy.apiassessment.model.Stock;

public class PortfolioCreator {

    // create list of stocks
    private static List<Investment> createStockInvestments() {
        List<Investment> techStockInvestments = new ArrayList<>();

        // create new stocks with their details
        // Stock appleStock = new Stock("MSFT", "Microsoft Corp", 150.25);
        // Stock alphabetStock = new Stock("NVDA", "NVIDIA Corp", 486.20);

        // techStockInvestments.add(appleStock);
        // techStockInvestments.add(alphabetStock);

        return techStockInvestments;
    }

    private static List<Investment> createETFInvestments() {
        List<Investment> healthcareETFInvestments = new ArrayList<>();

        // create new stocks with their details
        // ETF vooETF = new ETF("VOO", "Vanguard S&P 500 ETF", 120.75);
        // ETF ijrETF = new ETF("IJR", "iShares Core SP Small-Cap ETF.", 180.40);

        // healthcareETFInvestments.add(vooETF);
        // healthcareETFInvestments.add(ijrETF);

        return healthcareETFInvestments;
    }

    // create portfolio of stocks
    public Portfolio createStocksPortfolio() {
        // create list of stock investments
        List<Investment> techStockInvestments = createStockInvestments();

        // create stocks portfolio with stocks and name
        Portfolio techStocks = new Portfolio("Tech Stocks", techStockInvestments);

        // set UUID random id for portfolio
        // techStocks.setPortfolioId();

        return techStocks;
    }

    public Portfolio createETFsPortfolio() {
        // create list of ETF investments
        List<Investment> healthcareETFInvestments = createETFInvestments();

        // create ETFs portfolio with ETFs and name
        Portfolio healthcareETFs = new Portfolio("Random ETFs", healthcareETFInvestments);

        // set UUID random id for portfolio
        // healthcareETFs.setPortfolioId();

        return healthcareETFs;
    }

}
