package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
public class Client extends BaseEntity {
	@Column(unique = true)		
	private String dni;
	@Basic(optional = false)
	private String name;
	@Basic(optional = false)
	private String surname;
	@Basic(optional = false)
	private String email;
	@Basic(optional = false)
	private String phone;
	
	private Address address;

	@OneToMany(mappedBy = "client")
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	@OneToMany(mappedBy = "client")
	private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();

	public Client(String dni) {
		this(dni, "no-name", "no-surname", "no-email", "no-phone",
				new Address());
	}

	Client() {
	}

	public Client(String dni, String name, String surname, String email,
			String phone, Address address) {
		ArgumentChecks.isNotBlank(dni);
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(email);
		ArgumentChecks.isNotBlank(phone);
		ArgumentChecks.isNotNull(address);

		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	/**
	 * Constructor
	 * 
	 * @param dni
	 * @param name
	 * @param surname
	 */
	public Client(String dni, String name, String surname) {
		this(dni, name, surname, "no-email", "no-phone", new Address());
	}

	/**
	 * @return the paymentMeans
	 */
	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}

	/**
	 * @return the paymentMeans
	 */
	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the vehicles
	 */
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	/**
	 * @return the vehicles
	 */
	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}

	public void setAddress(Address address2) {
		this.address = address2;
		
	}

}
