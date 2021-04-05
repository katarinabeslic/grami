package com.njt.projekat.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_order")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "date_time")
	private Date dateAndTime;

	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "currency")
	private String currency;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "order_status")
	private String orderStatus;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartItems;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private CardInformation cardInformation;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Order() {
	
	}
	
	public Order(double price, String currency, String method, String intent, String description) {
		this.totalPrice = price;
		this.currency = currency;
		this.paymentMethod = method;
	}

	public Order(Timestamp dateAndTime, double totalPrice, String currency, String paymentMethod, String orderStatus, List<CartItem> cartItems, CardInformation cardInformation, Address address, User user) {
		this.dateAndTime = dateAndTime;
		this.totalPrice = totalPrice;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.orderStatus = orderStatus;
		this.cartItems = cartItems;
		this.cardInformation = cardInformation;
		this.address = address;
		this.user = user;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public CardInformation getCardInformation() {
		return cardInformation;
	}

	public void setCardInformation(CardInformation cardInformation) {
		this.cardInformation = cardInformation;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order{" +
				"dateAndTime=" + dateAndTime +
				", totalPrice=" + totalPrice +
				", currency='" + currency + '\'' +
				", paymentMethod='" + paymentMethod + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", cartItems=" + cartItems +
				", cardInformation=" + cardInformation.getCardNumber() +
				", address=" + address.getStreet() +
				", user=" + user.getFirstName() + " " + user.getLastName() +
				'}';
	}
}
