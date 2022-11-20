package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TProfessionalgroups")
public class ProfessionalGroup extends BaseEntity {
	@Column(unique = true)
	private String name;
	@Column(name = "PRODUCTIVITYBONUSPERCENTAGE")
	private double productivityRate;
	@Column(name = "TRIENNIUMPAYMENT")
	private double trienniumSalary;

	public ProfessionalGroup() {
	}

	public ProfessionalGroup(String name, double productivityRate,
			double trienniumSalary) {
		this.name = name;
		this.productivityRate = productivityRate;
		this.trienniumSalary = trienniumSalary;
	}

	public String getName() {
		return this.name;
	}

	public double getProductivityRate() {
		return this.productivityRate;
	}

	public double getTrieniumSalary() {
		return this.trienniumSalary;
	}

	public void setName(String name2) {
		this.name = name2;
	}

	public void setProductivityRate(double productivityRate) {
		this.productivityRate = productivityRate;
	}

	public void setTrienniumSalary(double trienniumSalary) {
		this.trienniumSalary = trienniumSalary;
	}

}
