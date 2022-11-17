package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "WORKORDER_ID", "MECHANIC_ID" }) })
public class Intervention extends BaseEntity {
	// natural attributes
	@Basic(optional = false)
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	private WorkOrder workOrder;
	private Mechanic mechanic;
	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	Intervention() {
	};

	/**
	 * @param date
	 * @param minutes
	 * @param workOrder
	 * @param mechanic
	 */
	public Intervention(LocalDateTime date, int minutes, WorkOrder workOrder,
			Mechanic mechanic) {
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(minutes >= 0);
		this.date = date.withNano(0);
		this.minutes = minutes;
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(LocalDateTime.now(), minutes, workOrder, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
			LocalDateTime date, int minutes) {
		this.date = date;
		this.minutes = minutes;
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, mechanic, workOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date)
				&& Objects.equals(mechanic, other.mechanic)
				&& Objects.equals(workOrder, other.workOrder);
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return the workOrder
	 */
	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	/**
	 * @return the mechanic
	 */
	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public double getAmount() {
		double pricePerHour = workOrder.getVehicle().getVehicleType().getPricePerHour();
		double pricePerMinute = pricePerHour / 60;
		double timeAmount = minutes * pricePerMinute;
		double substitutionAmount = 0.0;
		for (Substitution substitution : substitutions) {
			substitutionAmount += substitution.getAmount();
		}
		return timeAmount + substitutionAmount;
	}

}
