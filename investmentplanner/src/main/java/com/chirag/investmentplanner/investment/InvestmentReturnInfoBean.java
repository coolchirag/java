package com.chirag.investmentplanner.investment;

public class InvestmentReturnInfoBean {

	private double totalInvestedAmount;
	private double earnedInterestAmount;
	private double maturityAmount;
	
	
	
	protected InvestmentReturnInfoBean(double totalInvestedAmount, double earnedInterestAmount, double maturityAmount) {
		super();
		this.totalInvestedAmount = totalInvestedAmount;
		this.earnedInterestAmount = earnedInterestAmount;
		this.maturityAmount = maturityAmount;
	}
	
	public double getTotalInvestedAmount() {
		return totalInvestedAmount;
	}

	public double getEarnedInterestAmount() {
		return earnedInterestAmount;
	}
	public double getMaturityAmount() {
		return maturityAmount;
	}
	@Override
	public String toString() {
		return "InvestmentReturnInfoBean [totalInvestedAmount=" + totalInvestedAmount + ", earnedInterestAmount="
				+ earnedInterestAmount + ", maturityAmount=" + maturityAmount + "]";
	}
	
	
	
}
