package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
public class Contract extends BaseEntity {
	
	private Mechanic mechanic;
	
	@Basic(optional = false)
	private LocalDate startDate;
	
	private double annualWage;
	
	private LocalDate endDate;
	
	private double settlement;
	
	private ContractState state;
	
	public enum ContractState{
		IN_FORCE, TERMINATED
	}

	public void _setMechanic(Mechanic mechanic2) {
		// TODO Auto-generated method stub
		
	}
}
