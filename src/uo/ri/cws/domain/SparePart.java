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
@Table(name = "TSpareparts")
public class SparePart extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String code;
	@Basic(optional = false)
	private String description;
	private double price;

	// accidental attributes
	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {
	};

	/**
	 * @param code
	 */
	public SparePart(String code) {
		this(code, "no-description", 0.0);
	}

	/**
	 * @param code
	 * @param description
	 * @param price
	 * @param substitutions
	 */
	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotBlank(code);
		ArgumentChecks.isNotBlank(description);
		ArgumentChecks.isTrue(price >= 0);
		this.code = code;
		this.description = description;
		this.price = price;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description
				+ ", price=" + price + "]";
	}

}
