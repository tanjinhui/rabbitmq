package org.rabbitmq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="webapp_ordaddress")
public class ordaddress implements java.io.Serializable {
	@Id
	@Column(name="addrid")
	private String addrID;
	@Column(name="orderid")
	private String orderID;
	@Column(name="addressdetail")
	private String addressDetail;
	@Column(name="telephone")
	private String telephone;
	@Column(name="truename")
	private String truename;

	public ordaddress() {
	}

	public ordaddress(String addrID) {
		this.addrID = addrID;
	}
	public String getaddrID() {
		return this.addrID;
	}

	public void setaddrID(String addrID) {
		this.addrID = addrID;
	}
	public String getaddressdetail() {
		return this.addressDetail;
	}

	public void setaddressdetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public String gettelephone() {
		return this.telephone;
	}

	public void settelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTruename() {
		return this.truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getOrderID() {
		return this.orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}


}
