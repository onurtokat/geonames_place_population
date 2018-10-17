package com.onurtokat.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onurtokat.domain.Place;

/**
 * @author onurtokat
 */
@Repository
public interface PlaceRepository extends CrudRepository<Place, String>{

}
