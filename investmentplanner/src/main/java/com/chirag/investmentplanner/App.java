package com.chirag.investmentplanner;

import com.chirag.investmentplanner.investment.InvestmentCalculator;
import com.chirag.investmentplanner.loan.LoanCalculator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println(InvestmentCalculator.cumulativeInterestInvestment(10000, 10, 36, 12));
        //System.out.println(InvestmentCalculator.cumulativeInterestRecurringInvestment(1000, 1000, 3, 12, 12, 10, 12));
        System.out.println(LoanCalculator.getLoanPaymentInfo(17500, 9f, 2000000, 20, 6));
    }
}
