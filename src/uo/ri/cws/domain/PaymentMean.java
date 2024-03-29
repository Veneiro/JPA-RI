package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TPaymentMeans")
public abstract class PaymentMean extends BaseEntity {
	// natural attributes
	private double accumulated = 0.0;

	// accidental attributes
	private Client client;
	@OneToMany(mappedBy = "paymentMean")
	private Set<Charge> charges = new HashSet<>();

	/**
	 * @return the accumulated
	 */
	public double getAccumulated() {
		return accumulated;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	public void pay(double importe) {
		this.accumulated += importe;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public int hashCode() {
		return Objects.hash(client);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentMean other = (PaymentMean) obj;
		return Objects.equals(client, other.client);
	}

}
