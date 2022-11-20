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
		Associations.Run.link(this, contract2);
	}

	public Payroll(Contract contract2, LocalDate date2) {
		Associations.Run.link(this, contract2);
		this.date = date2;
	}

	public Payroll(Contract contract2, LocalDate d, double monthlyWage2,
			double extra, double productivity, double trienniums, double tax,
			double nic2) {
		Associations.Run.link(this, contract2);
		this.date = d;
		this.monthlyWage = monthlyWage2;
		this.bonus = extra;
		this.productivityBonus = productivity;
		this.trienniumPayment = trienniums;
		this.incomeTax = tax;
		this.nic = nic2;
	}

	public String getContractId() {
		return contract.getId();
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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

	public double getNetWage() {
		double aux = bonus + monthlyWage + productivityBonus + trienniumPayment;
		return aux + (aux * incomeTax);
	}

}
