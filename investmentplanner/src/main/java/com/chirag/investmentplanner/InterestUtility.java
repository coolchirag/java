package com.chirag.investmentplanner;

public class InterestUtility {

	public static float calculateInterestRate(float annualInterestRate, int investmentPeriodInMonths)
	{
		return investmentPeriodInMonths*annualInterestRate/12;
	}
	
	public static double calculateInterestAmount(float interestRate, double amount)
	{
		return amount*interestRate/100;
	}
}
