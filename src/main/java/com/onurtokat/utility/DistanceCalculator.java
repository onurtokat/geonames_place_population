package com.onurtokat.utility;

/**
 * DistanceCalculator class calculate distance using Haversine method
 * between two coordinates
 *
 * @author onurtokat
 */
public class DistanceCalculator {

    // Radius of the earth
    public static final int R = 6371;

    /**
     * Calculate distance between two points in latitude and longitude.
     * Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point in meters
     * @returns Distance in KiloMeters
     */
    /**
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @return distance value as kilometer
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return (Math.sqrt(distance)) / 1000;
    }
}
