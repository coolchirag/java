package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;

public class FinalLoanPaymentInfoBean {

	private double totalPayedAmount;
	private double totalInterestPayedAmount;
	private List<Double> interestPayedPerEmi;
	private List<Double> principalAmountPayedPerEmi;
	private List<YearlyLoanPaymentInfoBean> yearlyLoanPaymentInfo;
	private int numberOfEMI;
	private double totalEarnedInterestOnInvestment;
	private double totalReturnOnInvestment;

	

	

	

	protected FinalLoanPaymentInfoBean(double totalPayedAmount, double totalInterestPayedAmount,
			List<Double> interestPayedPerEmi, List<Double> principalAmountPayedPerEmi,
			List<YearlyLoanPaymentInfoBean> yearlyLoanPaymentInfo, int numberOfEMI,
			double totalEarnedInterestOnInvestment) {
		super();
		this.totalPayedAmount = totalPayedAmount;
		this.totalInterestPayedAmount = totalInterestPayedAmount;
		this.interestPayedPerEmi = interestPayedPerEmi;
		this.principalAmountPayedPerEmi = principalAmountPayedPerEmi;
		this.yearlyLoanPaymentInfo = yearlyLoanPaymentInfo;
		this.numberOfEMI = numberOfEMI;
		this.totalEarnedInterestOnInvestment = totalEarnedInterestOnInvestment;
	}

	public double getTotalPayedAmount() {
		return totalPayedAmount;
	}

	public double getTotalInterestPayedAmount() {
		return totalInterestPayedAmount;
	}

	public int getNumberOfEMI() {
		return numberOfEMI;
	}

	public List<Double> getInterestPayedPerEmi() {
		return interestPayedPerEmi;
	}

	public List<Double> getPrincipalAmountPayedPerEmi() {
		return principalAmountPayedPerEmi;
	}

	public void setTotalPayedAmount(double totalPayedAmount) {
		this.totalPayedAmount = totalPayedAmount;
	}

	public void setTotalInterestPayedAmount(double totalInterestPayedAmount) {
		this.totalInterestPayedAmount = totalInterestPayedAmount;
	}

	public void setNumberOfEMI(int numberOfEMI) {
		this.numberOfEMI = numberOfEMI;
	}

	public List<YearlyLoanPaymentInfoBean> getYearlyLoanPaymentInfo() {
		return yearlyLoanPaymentInfo;
	}

	public void setYearlyLoanPaymentInfo(List<YearlyLoanPaymentInfoBean> yearlyLoanPaymentInfo) {
		this.yearlyLoanPaymentInfo = yearlyLoanPaymentInfo;
	}

	public void addYearlyLoanPaymentInfo(YearlyLoanPaymentInfoBean yearlyLoanPaymentInfo) {
		if (this.yearlyLoanPaymentInfo == null) {
			this.yearlyLoanPaymentInfo = new ArrayList<YearlyLoanPaymentInfoBean>();
		}
		this.yearlyLoanPaymentInfo.add(yearlyLoanPaymentInfo);
	}

	private String toStringOfYearlyLoanPaymentInfo()
	{
		final StringBuilder sb = new StringBuilder();
		for(YearlyLoanPaymentInfoBean bean : yearlyLoanPaymentInfo)
		{
			sb.append("\n");
			sb.append(bean.toString());
		};
		return sb.toString();
	}
	
	public double getTotalSavedIncomeTax()
	{
		double totalSavedIncomeTax = 0;
		for(YearlyLoanPaymentInfoBean bean : yearlyLoanPaymentInfo)
		{
			totalSavedIncomeTax = totalSavedIncomeTax + bean.getSavedIncomeTax();
		}
		return totalSavedIncomeTax;
	}
	
	
	
	

	public double getTotalEarnedInterestOnInvestment() {
		return totalEarnedInterestOnInvestment;
	}

	public double getTotalBenifitOnInvestment()
	{
		return totalEarnedInterestOnInvestment+getTotalSavedIncomeTax();
	}
	public double getDifferenceBetweenLoanAndInvestment()
	{
		return totalInterestPayedAmount - getTotalBenifitOnInvestment();
	}
	@Override
	public String toString() {
		return "FinalLoanPaymentInfoBean [totalPayedAmount=" + totalPayedAmount + ", totalInterestPayedAmount=" + totalInterestPayedAmount
				+ ", numberOfEMI=" + numberOfEMI + ",\n Total save incometax "+getTotalSavedIncomeTax()+" totalEarnedInterestOnInvestment="+ totalEarnedInterestOnInvestment+
				 ",\n TotalBenifitOnInvestment = "+getTotalBenifitOnInvestment()+", DifferenceBetweenLoanAndInvestment="+getDifferenceBetweenLoanAndInvestment()/*+ ", interestPayedPerEmi=" + interestPayedPerEmi
				+ ", principalAmountPayedPerEmi=" + principalAmountPayedPerEmi*/ + ", yearlyLoanPaymentInfo="
				+ toStringOfYearlyLoanPaymentInfo() +"]";
	}
	
}
