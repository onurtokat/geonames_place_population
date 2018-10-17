package com.onurtokat.domain;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Place POJO is used as data model for mongo db
 * name field is indexed due to using place name queries
 *
 * @author onurtokat
 */
@Document
public class Place {

	@Id
	private long geonameid;
	
	@Indexed(name = "name_index", direction = IndexDirection.ASCENDING)
	public String name;
	private double latitude;
	private double longitude;
	private BigInteger population;

	public long getGeonameid() {
		return geonameid;
	}

	public void setGeonameid(long geonameid) {
		this.geonameid = geonameid;
	}

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

	/**
	 * toString method is overriden
	 *
	 * @return json format of the place model
	 */
	@Override
	public String toString() {

		return "{" + "\"placename\":\"" + name + '\"' + ", \"latitude\":" + latitude + ", \"longitude\":" + longitude
				+ ", \"population\":" + population + '}';
	}
}
