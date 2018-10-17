package com.onurtokat.utility;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DistanceCalculatorTest class checks Haversine formula correctness.
 * The distance between given double coordinations should be less and
 * equals than 30 km.
 *
 * @author onurtokat
 */
public class DistanceCalculatorTest {

    private double lat1;
    private double lat2;
    private double long1;
    private double long2;


    /**
     * setting init values before do tests
     */
    @Before
    public void init() {
        lat1 = 41.05421;
        long1 = 28.99673;
        lat2 = 41.17418;
        long2 = 29.08908;
    }

    /**
     * Calculate distance between two points in latitude and longitude.
     * Uses Haversine method as its base.
     * <p>
     * Distance between each coordinations should be less and equals than 30
     */
    @Test
    public void distanceTest() {
        assertTrue(DistanceCalculator.distance(lat1, lat2, long1, long2) <= 30);
    }

}