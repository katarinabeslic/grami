package com.njt.projekat.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vinyl")
public class Vinyl implements Serializable {

	private static final long serialVersionUID = 8822676566899316380L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "vinyl_name")
	private String vinylName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	@ManyToOne
	@JoinColumn(name = "record_label_id")
	private RecordLabel recordLabel;
	
	@ManyToOne
	@JoinColumn(name = "format_id")
	private Format format;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "vinyl_genre",
				joinColumns = @JoinColumn(name = "vinyl_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
	private Collection<Genre> genres;
	
	@OneToMany(mappedBy = "vinyl", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Song> songs;
	
	public Vinyl() {
		
	}
	
	public Vinyl(int id, String vinylName, String description, String imgUrl, double price, int quantity) {
		super();
		this.id = id;
		this.vinylName = vinylName;
		this.description = description;
		this.imgUrl = imgUrl;
		this.price = price;
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getVinylName() {
		return vinylName;
	}

	public void setVinylName(String vinylName) {
		this.vinylName = vinylName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Collection<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Collection<Genre> genres) {
		this.genres = genres;
	}

	public Collection<Song> getSongs() {
		return songs;
	}

	public void setSongs(Collection<Song> songs) {
		this.songs = songs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public RecordLabel getRecordLabel() {
		return recordLabel;
	}

	public void setRecordLabel(RecordLabel recordLabel) {
		this.recordLabel = recordLabel;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vinylName == null) ? 0 : vinylName.hashCode());
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
		Vinyl other = (Vinyl) obj;
		if (vinylName == null) {
			if (other.vinylName != null)
				return false;
		} else if (!vinylName.equals(other.vinylName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vinyl [id=" + id + ", vinylName=" + vinylName + ", description=" + description + ", imgUrl=" + imgUrl
				+ ", price=" + price + ", quantity=" + quantity + ", artist=" + artist + ", recordLabel=" + recordLabel
				+ ", format=" + format + ", genres=" + genres + ", songs=" + songs + "]";
	}


    public void decreaseQuantity(int quantity) {
		this.quantity -= quantity;
    }

    public void increaseQuantity(int quantity) {
		this.quantity += quantity;
    }
}
