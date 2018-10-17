package com.onurtokat.services;

import java.util.ArrayList;
import java.util.List;

import com.onurtokat.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.onurtokat.domain.Place;

/**
 * PlaceServiceImpl uses PlaceService interface's methods
 * to query usinf CRUD to mongo DB
 *
 * @author onurtokat
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    MongoTemplate mongoTemplate;

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    /**
     * listAll() method returns whole data from mongo db as list
     *
     * @return whole places return as List
     */
    @Override
    public List<Place> listAll() {
        List<Place> places = new ArrayList<>();
        placeRepository.findAll().forEach(places::add);
        return places;
    }

    /**
     * findByName method accept place name and queries to mongo db.
     * Place name is scan on mongo db as similar and lowercase.
     *
     * @param name place name
     * @return filtered place list
     */
    @Override
    public List<Place> findByName(String name) {
        Criteria regex = Criteria.where("name").regex(".*" + name + ".*", "i");
        List<Place> places = mongoTemplate.find(new Query().addCriteria(regex).with(new Sort(Sort.Direction.ASC,
                "name")), Place.class);
        return places;
    }

    /**
     * geonameid is used due to unique index field. It provides fast
     * query timing
     *
     * @param geonameid unique id
     * @return place List
     */
    @Override
    public List<Place> findByGeonameid(long geonameid) {
        Criteria regex = Criteria.where("geonameid").is(geonameid);
        List<Place> places = mongoTemplate.find(new Query().addCriteria(regex), Place
                .class);
        return places;
    }
}
