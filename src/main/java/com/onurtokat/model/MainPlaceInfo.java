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

	/**
	 * @param name
	 * @param latitude
	 * @param longitude
	 * @param population
	 * @param subPlaceCount
	 * @param listOfPlaceInfo
	 */
	public MainPlaceInfo(String name, double latitude, double longitude, BigInteger population, int subPlaceCount,
			List<Place> listOfPlaceInfo) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.population = population;
		this.subPlaceCount = subPlaceCount;
		this.listOfPlaceInfo = listOfPlaceInfo;
	}

	@Override
	public String toString() {

		return "{" + "\"placename\":\"" + name + '\"' + ", \"latitude\":" + latitude + ", \"longitude\":" + longitude
				+ ", \"population\":" + population + ", \"subPlaceCount\":" + subPlaceCount + ", \"SubPlaceInfo\":"
				+ listOfPlaceInfo + '}';
	}
}
