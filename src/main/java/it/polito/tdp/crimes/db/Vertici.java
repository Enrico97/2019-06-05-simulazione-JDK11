package it.polito.tdp.crimes.db;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;

public class Vertici{
	
	private double lon;
	private double lat;
	private int id;
	private LatLng centro;
	
	public Vertici(double lon, double lat, int id) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.id = id;
	}

	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Vertici other = (Vertici) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public LatLng centro(double lon, double lat) {
		LatLng centro = new LatLng(lon, lat);
		return centro;
	}

}
