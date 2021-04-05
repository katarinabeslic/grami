package com.njt.projekat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne
	@JoinColumn(name = "vinyl_id")
	private Vinyl vinyl;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	public CartItem() {
	
	}
	
	public CartItem(Vinyl vinyl, int quantity, User user) {
		this.vinyl = vinyl;
		this.quantity = quantity;
		this.user = user;
	}

	public CartItem(Vinyl vinyl, int quantity, User user, Order order) {
		this.vinyl = vinyl;
		this.quantity = quantity;
		this.user = user;
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vinyl getVinyl() {
		return vinyl;
	}

	public void setVinyl(Vinyl vinyl) {
		this.vinyl = vinyl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vinyl == null) ? 0 : vinyl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (vinyl == null) {
			if (other.vinyl != null)
				return false;
		} else if (!vinyl.equals(other.vinyl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CartItem{" +
				"vinyl=" + vinyl.getVinylName() +
				", quantity=" + quantity +
				", user=" + user.getUsername() +
				", order=ORD-" + order.getId() + " : " + order.getTotalPrice() +
				'}';
	}

    public void increaseQuantity(int qty) {
		int quantity = this.quantity + qty;
		setQuantity(quantity);
    }
}
