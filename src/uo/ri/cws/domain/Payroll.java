package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
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
}
