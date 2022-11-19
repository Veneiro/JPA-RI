package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVehicleTypes")
public class VehicleType extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String name;
	private double pricePerHour; // /60*costeporhora

	// accidental attributes
	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {
	}

	/**
	 * @param name
	 */
	public VehicleType(String name) {
		this(name, 0.0);
	}

	/**
	 * @param name
	 * @param pricePerHour
	 * @param vehicles
	 */
	public VehicleType(String name, double pricePerHour) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isTrue(pricePerHour >= 0);
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the pricePerHour
	 */
	public double getPricePerHour() {
		return pricePerHour;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name);
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
				+ "]";
	}

}
