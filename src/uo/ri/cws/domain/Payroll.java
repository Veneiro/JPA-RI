package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

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
		ArgumentChecks.isNotNull(contract2);
		Associations.Run.link(this, contract2);
		this.date = LocalDate.now();
		this.monthlyWage = contract2.getAnnualBaseWage() / 14;
		this.nic = Math.floor(
				((contract2.getAnnualBaseWage() * 0.05) / 12) * 100) / 100;
		calculateTrienniumPayment();
		calculateProductivityBonus(contract2.getMechanic().get().getAssigned());
		calculateIncomeTax();
	}

	public Payroll(Contract contract2, LocalDate date2) {
		ArgumentChecks.isNotNull(date2);
		Associations.Run.link(this, contract2);
		this.date = date2;
		this.monthlyWage = contract2.getAnnualBaseWage() / 14;
		this.nic = Math.floor(
				((contract2.getAnnualBaseWage() * 0.05) / 12) * 100) / 100;
		calculateProductivityBonus(contract2.getMechanic().get().getAssigned());
		calculateTrienniumPayment();
		calculateIncomeTax();
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
		calculateProductivityBonus(
				contract2.getMechanic().get()._getAssigned());
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
	
	private void calculateIncomeTax() {
		if (monthlyWage * 12 >= 0 && monthlyWage * 12 <= 12450) {
			this.incomeTax = this.monthlyWage * 2 * 0.19;
		} else if (monthlyWage * 12 > 12450 && monthlyWage * 12 <= 20200) {
			this.incomeTax = this.monthlyWage * 2 * 0.24;
		} else if (monthlyWage * 12 > 20200 && monthlyWage * 12 <= 35200) {
			this.incomeTax = this.monthlyWage * 2 * 0.30;
		} else if (monthlyWage * 12 > 35200 && monthlyWage * 12 <= 60000) {
			this.incomeTax = this.monthlyWage * 2 * 0.37;
		} else if (monthlyWage * 12 > 60000 && monthlyWage * 12 <= 300000) {
			this.incomeTax = this.monthlyWage * 2 * 0.45;
		} else {
			this.incomeTax = this.monthlyWage * 2 * 0.47;
		}
	}

	public double getBonus() {
		var p = Period.between(date, LocalDate.now());
		var l = LocalDate.of(date.getYear(), Month.JUNE, 30);
		if (l.isBefore(contract.getEndDate().get())
				&& l.isAfter(contract.getStartDate())) {
			this.bonus = monthlyWage;
		}
		return this.bonus;
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

	private void calculateProductivityBonus(Set<WorkOrder> wo) {
		var opened = 0.0;
		for (WorkOrder workOrder : wo) {
			if (workOrder.getState() == WorkOrderState.OPEN
					|| workOrder.getState() == WorkOrderState.INVOICED) {
				opened += workOrder.getAmount();
			}
		}
		this.productivityBonus = opened * contract.getProfessionalGroup()
				.getProductivityBonusPercentage() / 1000;
	}

	public double getProductivityBonus() {
		return this.productivityBonus;
	}

	private void calculateTrienniumPayment() {
		var p = Period.between(contract.getStartDate(),
				contract.getEndDate().get());
		var y = p.getYears() / 3;
		this.trienniumPayment = y
				* this.contract.getProfessionalGroup().getTrieniumSalary()*10;
	}

	public double getTrienniumPayment() {
		return this.trienniumPayment;
	}

	public double getNetWage() {
		double aux = bonus + monthlyWage + productivityBonus + trienniumPayment;
		return aux + (aux * incomeTax);
	}

}
