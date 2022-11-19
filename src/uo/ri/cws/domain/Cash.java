package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TCASHES")
public class Cash extends PaymentMean {

	public Cash() {
	}

	public Cash(Client client) {
		Associations.Pay.link(client, this);
	}

}
