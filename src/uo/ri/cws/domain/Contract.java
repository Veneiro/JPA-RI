package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TContracts")
public class Contract extends BaseEntity {

	private Mechanic mechanic;
	private Mechanic firedMechanic;
	@JoinColumn(name = "PROFESSIONALGROUP_ID")
	private ProfessionalGroup pg;

	@Basic(optional = false)
	private LocalDate startDate;

	@Column(name = "ANNUALBASEWAGE")
	private double annualWage;

	@Basic(optional = false)
	private LocalDate endDate;

	private double settlement;

	@Enumerated(EnumType.STRING)
	private ContractState state;

	@JoinColumn(name = "CONTRACTTYPE_ID")
	private ContractType type;

	public enum ContractState {
		IN_FORCE, TERMINATED
	}

	public Contract() {
	}

	public Contract(Mechanic mechanic2, ContractType type,
			ProfessionalGroup group, double wage) {
		this.mechanic = mechanic2;
		this.state = ContractState.IN_FORCE;
		this.pg = group;
	}

	public Contract(Mechanic mechanic2, ContractType type,
			ProfessionalGroup group, LocalDate endDate2, double wage) {
		this.mechanic = mechanic2;
		this.type = type;
		this.annualWage = wage;
		this.endDate = endDate2;
		this.state = ContractState.IN_FORCE;
		this.pg = group;
	}

	public void setMechanic(Mechanic optional) {
		this.mechanic = optional;
	}

	public void setFiredMechanic(Mechanic mechanic2) {
		this.firedMechanic = mechanic2;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public ContractState getState() {
		return this.state;
	}

	public Optional<Mechanic> getMechanic() {
		return Optional.of(this.mechanic);
	}

	public Optional<Mechanic> getFiredMechanic() {
		return Optional.of(this.firedMechanic);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public double getAnnualBaseWage() {
		return annualWage;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public double getSettlement() {
		return settlement;
	}

	public ContractType getContractType() {
		return type;
	}

	public void terminate() {
		this.state = ContractState.TERMINATED;
	}

	public ProfessionalGroup getProfessionalGroup() {
		return this.pg;
	}
}
