package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;

import com.chirag.investmentplanner.InterestUtility;

public class LoanCalculator {

	public static FinalLoanPaymentInfoBean getLoanPaymentInfo(double emiAmount, float annualInterestRate, double loanAmount)
	{
		double remainingLoanAmount = loanAmount;
		double totalPayedAmount = 0;
		double totalPayedInterestAmount = 0;
		int numOfEmi = 0;
		List<Double> interestPayedPerEmi = new ArrayList<Double>();
		List<Double> principalPayedPerEmi = new ArrayList<Double>();
		while(remainingLoanAmount>0)
		{
			totalPayedAmount = totalPayedAmount + emiAmount;
			numOfEmi++;
			
			double payedInterestAmount = InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRate, 1), remainingLoanAmount);
			remainingLoanAmount = remainingLoanAmount - (emiAmount - payedInterestAmount);
			totalPayedInterestAmount = totalPayedInterestAmount + payedInterestAmount;
			interestPayedPerEmi.add(payedInterestAmount);
			principalPayedPerEmi.add(emiAmount-payedInterestAmount);
			System.out.println("remianing loan amount : "+remainingLoanAmount+", payedInterestAmount : "+payedInterestAmount);
			if(remainingLoanAmount > loanAmount)
			{
				System.out.println("Wrong remaining amount : "+remainingLoanAmount);
				break;
			}
		}
		return new FinalLoanPaymentInfoBean(totalPayedAmount, totalPayedInterestAmount, interestPayedPerEmi, principalPayedPerEmi, numOfEmi);
	}
}
