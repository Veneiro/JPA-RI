package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TWorkorders", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "VEHICLE_ID", "DATE" }) })
public class WorkOrder extends BaseEntity {
	public enum WorkOrderState {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	// natural attributes
	@Basic(optional = false)
	private LocalDateTime date;
	@Basic(optional = false)
	private String description;
	private double amount = 0.0;
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {
	}

	/**
	 * @param vehicle
	 */
	public WorkOrder(Vehicle vehicle) {
		this(LocalDateTime.now(), "no-description", vehicle);
	}

	/**
	 * @param date
	 * @param description
	 * @param vehicle
	 */
	public WorkOrder(LocalDateTime date, String description, Vehicle vehicle) {
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotBlank(description);

		this.date = LocalDateTime.of(date.getYear(), date.getMonth(),
				date.getDayOfMonth(), date.getHour(), date.getMinute(),
				date.getSecond(), (date.getNano() / 1000) * 1000);
		this.description = description;
		this.vehicle = vehicle;
		Associations.Fix.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(LocalDateTime.now(), description, vehicle);
	}

	public WorkOrder(Vehicle vehicle, LocalDateTime now, String description) {
		this(now, description, vehicle);
	}

	public WorkOrder(Vehicle v, LocalDateTime atStartOfDay) {
		this(v, atStartOfDay, "no-description");
	}

	public void setState(WorkOrderState state) {
		this.state = state;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @return the state
	 */
	public WorkOrderState getState() {
		return state;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @return the mechanic
	 */
	public Mechanic getMechanic() {
		return mechanic;
	}

	/**
	 * @return the invoice
	 */
	public Invoice getInvoice() {
		return invoice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date)
				&& Objects.equals(vehicle, other.vehicle);
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description
				+ ", amount=" + amount + ", state=" + state + ", vehicle="
				+ vehicle + "]";
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or -
	 *                               The work order is not linked with the
	 *                               invoice
	 */
	public void markAsInvoiced() {
		if (this.state != WorkOrderState.FINISHED
				|| this.getInvoice() == null) {
			throw new IllegalStateException(
					"Trying to invoice a not finished workOrder");
		}
		this.state = WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state, or - The work order is not linked
	 *                               with a mechanic
	 */
	public void markAsFinished() {
		this.state = WorkOrderState.FINISHED;
		this.amount = 0;
		for (Intervention intervention : interventions) {
			this.amount += intervention.getAmount();
		}
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or -
	 *                               The work order is still linked with the
	 *                               invoice
	 */
	public void markBackToFinished() {

	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN state,
	 *                               or - The work order is already linked with
	 *                               another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		Associations.Assign.link(mechanic, this);
		this.state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * state back to OPEN
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state
	 */
	public void desassign() {
		Associations.Assign.unlink(mechanic, this);
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED
	 *                               state
	 */
	public void reopen() {
		this.state = WorkOrderState.OPEN;
		desassign();
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public boolean isOpen() {
		return state == WorkOrderState.OPEN;
	}

	public boolean isInvoiced() {
		return state == WorkOrderState.INVOICED;
	}

	public boolean isFinished() {
		if (this.state == WorkOrderState.FINISHED) {
			return true;
		}
		return false;
	}

	public void setStatusForTesting(WorkOrderState invoiced) {
		this.state = invoiced;
	}

	public void setAmountForTesting(double money) {
		this.amount = money;
	}

}
