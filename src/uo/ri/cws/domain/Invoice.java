package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TINVOICES")
public class Invoice extends BaseEntity {
	public enum InvoiceState {
		NOT_YET_PAID, PAID
	}

	// natural attributes
	@Column(unique = true)
	private Long number;
	@Basic(optional = false)
	private LocalDate date = LocalDate.now();
	private double amount;
	private double vat;
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice() {
	};

	public Invoice(Long number) {
		// call full constructor with sensible defaults
		this.number = number;
		if (LocalDate.now().isBefore(LocalDate.of(2012, 7, 1))) {
			this.vat = 0.18;
		} else {
			this.vat = 0.21;
		}
		computeAmount();
	}

	public Invoice(Long number, LocalDate date) {
		// call full constructor with sensible defaults
		this.number = number;
		this.date = date;
		if (date.isBefore(LocalDate.of(2012, 7, 1))) {
			this.vat = 0.18;
		} else {
			this.vat = 0.21;
		}
		computeAmount();
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		for (WorkOrder workOrder : workOrders) {
			if (workOrder.getState() != WorkOrderState.FINISHED) {
				throw new IllegalStateException(
						"One workOrder given is not finished");
			}
		}
		// check arguments (always), through IllegalArgumentException
		// store the number
		this.number = number;
		// store a copy of the date
		this.date = date;
		// add every work order calling addWorkOrder( w )
		for (int i = 0; i < workOrders.size(); i++) {
			addWorkOrder(workOrders.get(i));
		}
		if (date.isBefore(LocalDate.of(2012, 7, 1))) {
			this.vat = 0.18;
		} else {
			this.vat = 0.21;
		}
		computeAmount();
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		this.amount = 0;
		for (WorkOrder wo : workOrders) {
			this.amount += wo.getAmount();
			wo.getAmount();
		}
		this.amount = Round.twoCents(this.amount + (this.amount * this.vat));
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (this.state != InvoiceState.NOT_YET_PAID) {
			throw new IllegalStateException("Invoice not yet paid");
		}

		workOrder.setState(WorkOrderState.INVOICED);
		Associations.ToInvoice.link(this, workOrder);

		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (this.state != InvoiceState.NOT_YET_PAID) {
			throw new IllegalStateException("Invoice not yet paid");
		}

		Associations.ToInvoice.unlink(this, workOrder);

		computeAmount();
		workOrder.setState(WorkOrderState.FINISHED);
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		if (!isNotSettled()) {
			throw new IllegalStateException();
		}
		this.state = InvoiceState.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public double getAmount() {
		return this.amount;
	}

	public boolean isNotSettled() {
		return this.state == InvoiceState.NOT_YET_PAID;
	}

	public Long getNumber() {
		return this.number;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public double getVat() {
		return this.vat;
	}

	public InvoiceState getState() {
		return this.state;
	}

}
