package com.chirag.investmentplanner.loan;

import java.util.ArrayList;
import java.util.List;

public class FinalLoanPaymentInfoBean {

	
	private double totalPayedAmount;
	private double totalInterestPayedAmount;
	private List<Double> interestPayedPerEmi;
	private List<Double> principalAmountPayedPerEmi;
	private int numberOfEMI;
	
	
	protected FinalLoanPaymentInfoBean(double totalPayedAmount, double totalInterestPayedAmount,
			List<Double> interestPayedPerEmi, List<Double> principalAmountPayedPerEmi, int numberOfEMI) {
		super();
		this.totalPayedAmount = totalPayedAmount;
		this.totalInterestPayedAmount = totalInterestPayedAmount;
		this.interestPayedPerEmi = interestPayedPerEmi;
		this.principalAmountPayedPerEmi = principalAmountPayedPerEmi;
		this.numberOfEMI = numberOfEMI;
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
	@Override
	public String toString() {
		return "FinalLoanPaymentInfoBean [totalPayedAmount=" + totalPayedAmount + ", totalInterestPayedAmount="
				+ totalInterestPayedAmount + ", interestPayedPerEmi=" + interestPayedPerEmi
				+ ", principalAmountPayedPerEmi=" + principalAmountPayedPerEmi + ", numberOfEMI=" + numberOfEMI + "]";
	}
	
	
	

	
	
	
}
