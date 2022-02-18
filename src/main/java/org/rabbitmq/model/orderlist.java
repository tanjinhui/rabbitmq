package org.rabbitmq.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="webapp_orderlist")
public class orderlist implements java.io.Serializable {
	@Id
	@Column(name="orderid")
	private String orderID;
	@Column(name="username")
	private String userName;
	@Column(name="totalprice")
	private float totalPrice;
	@Column(name="totalnum")
	private Integer  totalNum;
	@Column(name="status")
	private Integer status;
	@Column(name="createtime")
	private Date createTime;

	public orderlist() {
	}

	public orderlist(String orderID) {
		this.orderID = orderID;
	}
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderID() {
		return this.orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
