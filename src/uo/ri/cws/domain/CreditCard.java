package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	@Basic(optional = false)
	private String type;
	@Basic(optional = false)
	private LocalDate validThru;
	
	public CreditCard() {
	}
	/**
	 * @param number
	 * @param type
	 * @param validThru
	 */
	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}
	public CreditCard(String number) {
		this(number, "UNKNOWN", LocalDate.now().plusDays(1));
	}
	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}
	public LocalDate getValidThru() {
		return this.validThru;
	}
	public String getType() {
		return this.type;
	}
	public String getNumber() {
		return this.number;
	}
	public boolean isValidNow() {
		if(LocalDate.now().isBefore(validThru)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void pay(double importe) {
		if(LocalDate.now().isAfter(this.validThru)) {
			throw new IllegalStateException("Credit card due date passed");
		}
		super.pay(importe);
	}
	
	public void setValidThru(LocalDate minusDays) {
		this.validThru = minusDays;
	}
}
