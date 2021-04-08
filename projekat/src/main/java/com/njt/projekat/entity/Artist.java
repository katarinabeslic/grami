package com.njt.projekat.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "artist")
public class Artist implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "stage_name")
	private String stageName;
	
	@OneToMany(mappedBy = "artist")
	@JsonIgnore
	private Collection<Vinyl> vinyls;

	public Artist() {
		// TODO Auto-generated constructor stub
	}

	public Artist(String stageName) {
		super();
		this.stageName = stageName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Collection<Vinyl> getVinyls() {
		return vinyls;
	}

	public void setVinyls(Collection<Vinyl> vinyls) {
		this.vinyls = vinyls;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stageName == null) ? 0 : stageName.hashCode());
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
		Artist other = (Artist) obj;
		if (stageName == null) {
			if (other.stageName != null)
				return false;
		} else if (!stageName.equals(other.stageName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Artist [stageName=" + stageName + "]";
	}

}
