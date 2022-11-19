package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "ContractTypes")
public class ContractType extends BaseEntity {

	@Column(unique = true)
	private String name;
	private double compensationDays;
}
