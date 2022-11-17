package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "INVOICE_ID", "PAYMENTMEAN_ID" }) })
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

	public Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		
		
		this.amount = amount;
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		paymentMean.pay(amount);
		// link invoice, this and paymentMean
		Associations.ToCharge.link(paymentMean, this, invoice);
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		paymentMean.pay(amount);
		// unlinks invoice, this and paymentMean
		this.invoice = null;
		this.paymentMean = null;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public PaymentMean getPaymentMean() {
		return this.paymentMean;
	}

	public double getAmount() {
		return this.amount;
	}

	public void _setPaymentMean(PaymentMean pm) {
		this.paymentMean = pm;
	}

	public void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
