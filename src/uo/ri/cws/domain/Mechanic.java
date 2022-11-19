package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String dni;
	@Basic(optional = false)
	private String surname;
	@Basic(optional = false)
	private String name;

	// accidental attributes
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Contract> contracts = new HashSet<>();

	Mechanic() {
	};

	/**
	 * @param dni
	 */
	public Mechanic(String dni) {
		this(dni, "no-surname", "no-name");
	}

	/**
	 * @param dni
	 * @param surname
	 * @param name
	 * @param assigned
	 * @param interventions
	 */
	public Mechanic(String dni, String surname, String name) {
		ArgumentChecks.isNotBlank(dni);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(name);
		this.dni = dni;
		this.surname = surname;
		this.name = name;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public void setName(String name2) {
		this.name = name2;
	}

	public void setSurname(String surname2) {
		this.surname = surname2;
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

}
