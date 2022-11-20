package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TPayrolls")
public class Payroll extends BaseEntity {

	private Contract contract;

	@Basic(optional = false)
	private LocalDate date;

	private double bonus;
	private double incomeTax;
	private double monthlyWage;
	private double nic;
	private double productivityBonus;
	private double trienniumPayment;

	public Payroll() {
	}

	public Payroll(Contract contract2) {
		this.contract = contract2;
	}

	public Payroll(Contract contract2, LocalDate date2) {
		this.contract = contract2;
		this.date = date2;
	}

	public String getContractId() {
		return contract.getId();
	}

	public Contract getContract() {
		return contract;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getBonus() {
		return bonus;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public double getMonthlyWage() {
		return monthlyWage;
	}

	public double getNIC() {
		return nic;
	}

	public double getProductivityBonus() {
		return productivityBonus;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

}
