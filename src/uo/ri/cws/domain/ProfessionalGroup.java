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
	private double productivityRate;
	private double trienniumSalary;
}
