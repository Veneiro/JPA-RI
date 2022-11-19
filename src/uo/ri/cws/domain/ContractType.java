package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
public class ContractType extends BaseEntity {
	
	@Column(unique = true)
	private String name;
	private double compensationDays;
}
