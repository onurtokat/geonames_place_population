package com.onurtokat.model;

import java.math.BigInteger;
import java.util.List;

import com.onurtokat.domain.Place;

/**
 * MainPlaceInfo is used for exact place name
 *
 * @author onurtokat
 */
public class MainPlaceInfo {

	private String name;
	private double latitude;
	private double longitude;
	private BigInteger population;
	private int subPlaceCount;
	private List<Place> listOfPlaceInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public BigInteger getPopulation() {
		return population;
	}

	public void setPopulation(BigInteger population) {
		this.population = population;
	}

	public int getSubPlaceCount() {
		return subPlaceCount;
	}

	public void setSubPlaceCount(int subPlaceCount) {
		this.subPlaceCount = subPlaceCount;
	}

	public List<Place> getListOfPlaceInfo() {
		return listOfPlaceInfo;
	}

	public void setListOfPlaceInfo(List<Place> listOfPlaceInfo) {
		this.listOfPlaceInfo = listOfPlaceInfo;
	}

	@Override
	public String toString() {

		return "{" + "\"placename\":\"" + name + '\"' + ", \"latitude\":" + latitude + ", \"longitude\":" + longitude
				+ ", \"population\":" + population + ", \"subPlaceCount\":" + subPlaceCount + ", \"SubPlaceInfo\":"
				+ listOfPlaceInfo + '}';
	}
}
