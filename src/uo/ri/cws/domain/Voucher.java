package uo.ri.cws.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TVouchers")
public class Voucher extends PaymentMean {
	@Column(unique = true)
	private String code;
	private double available = 0.0;
	@Basic(optional = false)
	private String description;

	public Voucher() {
	}

	public Voucher(String code, String description, double d) {
		this.code = code;
		this.description = description;
		this.available = d;
	}

	public Voucher(String code, double d) {
		this(code, "no-description", d);
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the
	 * available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		if (available < amount) {
			throw new IllegalStateException(
					"Voucher does not have enought availible to pay");
		}
		this.available -= amount;
		super.pay(amount);
	}

	public double getAvailable() {
		return this.available;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCode() {
		return this.code;
	}

}
