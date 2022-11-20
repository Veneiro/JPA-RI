package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TContractTypes")
public class ContractType extends BaseEntity {

	@Column(unique = true)
	private String name;
	private double compensationDays;

	@OneToMany(mappedBy = "type")
	private Set<Contract> contracts = new HashSet<Contract>();

	public ContractType() {
	}

	public ContractType(String name, double compensationDays) {
		this.name = name;
		this.compensationDays = compensationDays;
	}

	public String getName() {
		return name;
	}

	public double getCompensationDays() {
		return compensationDays;
	}

	public Set<Contract> getContracts() {
		return new HashSet<Contract>(contracts);
	}

	Set<Contract> _getContracts() {
		return this.contracts;
	}

}
