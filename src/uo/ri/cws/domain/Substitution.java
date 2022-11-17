package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "SPAREPART_ID",
		"INTERVENTION_ID" }) })
public class Substitution extends BaseEntity {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	private Intervention intervention;

	public Substitution() {

	}

	/**
	 * @param quantity
	 * @param sparePart
	 * @param intervention
	 */
	public Substitution(int quantity, SparePart sparePart,
			Intervention intervention) {
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(intervention);
		ArgumentChecks.isTrue(quantity > 0);
		this.quantity = quantity;
		Associations.Substitute.link(sparePart, this, intervention);
	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(quantity, sparePart, intervention);
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return the sparePart
	 */
	public SparePart getSparePart() {
		return sparePart;
	}

	/**
	 * @return the intervention
	 */
	public Intervention getIntervention() {
		return intervention;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	@Override
	public int hashCode() {
		int v = Objects.hash(intervention, sparePart);
		return v;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Substitution other = (Substitution) obj;
		return Objects.equals(intervention, other.intervention)
				&& Objects.equals(sparePart, other.sparePart);
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
				+ ", intervention=" + intervention + "]";
	}

	public double getAmount() {
		return quantity * sparePart.getPrice();
	}

}
