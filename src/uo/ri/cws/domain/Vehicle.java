package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVEHICLES")
public class Vehicle extends BaseEntity {
	@Column(unique = true)
	private String plateNumber;
	@Basic(optional = false)
	@Column(name = "BRAND")
	private String make;
	@Basic(optional = false)
	private String model;

	private Client client;
	private VehicleType vehicleType;
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

	Vehicle() {
	}

	/**
	 * @param plateNumber
	 */
	public Vehicle(String plateNumber) {
		this(plateNumber, "no-make", "no-model");
	}

	/**
	 * @param plateNumber
	 * @param make
	 * @param model
	 */
	public Vehicle(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank(plateNumber);
		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}

	/**
	 * @return the plateNumber
	 */
	public String getPlateNumber() {
		return plateNumber;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	/* package */ void _setClient(Client client) {
		this.client = client;
	}

	void _setVehicleType(VehicleType vehicleType2) {
		this.vehicleType = vehicleType2;
	}

	public VehicleType getVehicleType() {
		return this.vehicleType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

	/**
	 * @return the workOrders
	 */
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	/**
	 * @return the workOrders
	 */
	public Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

}
