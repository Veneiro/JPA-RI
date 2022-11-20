package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TContracts")
public class Contract extends BaseEntity {

	private Mechanic mechanic;
	private Mechanic firedMechanic;
	@ManyToOne
	@JoinColumn(name = "PROFESSIONALGROUP_ID")
	private ProfessionalGroup group;

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

	@OneToMany(mappedBy = "contract")
	private Set<Payroll> payrolls = new HashSet<Payroll>();

	public Contract() {
	}

	public Contract(Mechanic mechanic2, ContractType type,
			ProfessionalGroup group, double wage) {
		Associations.Hire.link(this, mechanic2);
		this.state = ContractState.IN_FORCE;
		Associations.Group.link(this, group);
		Associations.Type.link(this, type);
	}

	public Contract(Mechanic mechanic2, ContractType type,
			ProfessionalGroup group, LocalDate endDate2, double wage) {
		Associations.Hire.link(this, mechanic2);
		Associations.Type.link(this, type);
		this.annualWage = wage;
		this.endDate = endDate2;
		this.state = ContractState.IN_FORCE;
		Associations.Group.link(this, group);
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	public void setMechanic(Mechanic optional) {
		this.mechanic = optional;
	}

	public void _setFiredMechanic(Mechanic mechanic2) {
		this.firedMechanic = mechanic2;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public ContractState getState() {
		return this.state;
	}

	public Optional<Mechanic> getMechanic() {
		return Optional.ofNullable(this.mechanic);
	}

	public Optional<Mechanic> getFiredMechanic() {
		return Optional.ofNullable(this.firedMechanic);
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
		return this.group;
	}

	public void setProfessionalGroup(ProfessionalGroup pg) {
		this.group = pg;
	}

	Set<Payroll> _getPayrolls() {
		return payrolls;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<Payroll>(payrolls);
	}
}
