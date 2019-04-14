package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;

public class FinalLoanPaymentInfoBean {

	
	private double totalPayedAmount;
	private double totalInterestPayedAmount;
	private List<Double> interestPayedPerAnual;
	
	
	
	protected FinalLoanPaymentInfoBean(double totalPayedAmount, double totalInterestPayedAmount) {
		super();
		this.totalPayedAmount = totalPayedAmount;
		this.totalInterestPayedAmount = totalInterestPayedAmount;
		interestPayedPerAnual = new ArrayList<Double>();
	}
	
	public double getTotalPayedAmount() {
		return totalPayedAmount;
	}
	public double getTotalInterestPayedAmount() {
		return totalInterestPayedAmount;
	}
	public List<Double> getInterestPayedPerAnual() {
		return interestPayedPerAnual;
	}
	
	void addInterestPayedPerAnnual(Double interestAmount)
	{
		interestPayedPerAnual.add(interestAmount);
	}

	@Override
	public String toString() {
		return "FinalLoanPaymentInfoBean [totalPayedAmount=" + totalPayedAmount + ", totalInterestPayedAmount="
				+ totalInterestPayedAmount + ", interestPayedPerAnual=" + interestPayedPerAnual + "]";
	}
	
	
}
