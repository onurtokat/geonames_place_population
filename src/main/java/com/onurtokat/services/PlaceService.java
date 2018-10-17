package com.onurtokat.services;

import java.util.List;

import com.onurtokat.domain.Place;

/**
 *
 * @author onurtokat
 */
public interface PlaceService {

	List<Place> listAll();

	List<Place> findByName(String name);
	
	List<Place> findByGeonameid(long geonameid);
}
