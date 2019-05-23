package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;

import com.chirag.investmentplanner.InterestUtility;

public class LoanCalculator {

	public static FinalLoanPaymentInfoBean getLoanPaymentInfo(double emiAmount, float annualInterestRate, double loanAmount, float incomeTaxSlab, float annualInterestRateForInvetment)
	{
		double remainingLoanAmount = loanAmount;
		double totalPayedAmount = 0;
		double totalPayedInterestAmount = 0;
		double totalEarnedInterestOnInvestment = 0;
		int numOfEmi = 0;
		List<Double> interestPayedPerEmi = new ArrayList<Double>();
		List<Double> principalPayedPerEmi = new ArrayList<Double>();
		List<YearlyLoanPaymentInfoBean> yearlyLoanPaymentInfoBeans = new ArrayList<YearlyLoanPaymentInfoBean>();
		YearlyLoanPaymentInfoBean yearlyLoanPaymentInfoBean = new YearlyLoanPaymentInfoBean(incomeTaxSlab);
		while(remainingLoanAmount>0)
		{
			totalPayedAmount = totalPayedAmount + emiAmount;
			numOfEmi++;
			yearlyLoanPaymentInfoBean.addTotalPayedAmount(emiAmount);
			double payedInterestAmount = InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRate, 1), remainingLoanAmount);
			double earnedInterestOnInvestment = InterestUtility.calculateInterestAmount(InterestUtility.calculateInterestRate(annualInterestRateForInvetment, 1), remainingLoanAmount);
			totalEarnedInterestOnInvestment = totalEarnedInterestOnInvestment+earnedInterestOnInvestment;
			remainingLoanAmount = remainingLoanAmount - (emiAmount - payedInterestAmount);
			totalPayedInterestAmount = totalPayedInterestAmount + payedInterestAmount;
			interestPayedPerEmi.add(payedInterestAmount);
			principalPayedPerEmi.add(emiAmount-payedInterestAmount);
			yearlyLoanPaymentInfoBean.addTotalInterestPayedAmount(payedInterestAmount);
			yearlyLoanPaymentInfoBean.addTotalPayedPrincipalAmount(emiAmount - payedInterestAmount);
			System.out.println("remianing loan amount : "+remainingLoanAmount+", payedInterestAmount : "+payedInterestAmount);
			if(remainingLoanAmount > loanAmount)
			{
				System.out.println("Wrong remaining amount : "+remainingLoanAmount);
				break;
			}
			
			if(numOfEmi%12 == 0)
			{
				yearlyLoanPaymentInfoBean.setRemainigLoanAmount(remainingLoanAmount);
				yearlyLoanPaymentInfoBeans.add(yearlyLoanPaymentInfoBean);
				yearlyLoanPaymentInfoBean = new YearlyLoanPaymentInfoBean(incomeTaxSlab);
			}
		}
		return new FinalLoanPaymentInfoBean(totalPayedAmount, totalPayedInterestAmount, interestPayedPerEmi, principalPayedPerEmi, yearlyLoanPaymentInfoBeans, numOfEmi, totalEarnedInterestOnInvestment);
	}
}
