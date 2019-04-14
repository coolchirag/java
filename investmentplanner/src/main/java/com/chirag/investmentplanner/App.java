package com.chirag.investmentplanner;

import com.chirag.investmentplanner.investment.InvestmentCalculator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println(InvestmentCalculator.cumulativeInterestInvestment(10000, 10, 36, 12));
        System.out.println(InvestmentCalculator.cumulativeInterestRecurringInvestment(1000, 1000, 1, 12, 12, 10, 12));
        //https://www.calculator.net/investment-calculator.html?ctype=endamount&ctargetamountv=1000000&cstartingprinciplev=20000&cyearsv=1&cinterestratev=6&ccontributeamountv=1000&ciadditionat1=monthly&printit=0&x=69&y=11
    }
}
