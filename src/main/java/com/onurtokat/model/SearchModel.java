package com.onurtokat.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * SearchModel is used as model for search operations
 *
 * @author onurtokat
 */
public class SearchModel {

    private Long geonameid;

    @NotNull
    private String name;

    @Min(0)
    private double radius;

    public Long getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(Long geonameid) {
        this.geonameid = geonameid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


}
