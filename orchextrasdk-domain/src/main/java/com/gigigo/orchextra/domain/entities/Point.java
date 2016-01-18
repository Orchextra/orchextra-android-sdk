package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class Point {

  public static final int EARTH_RADIUS = 6371;

  private double lat;
  private double lng;

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getDistanceFromLatLonInKm(Point point) {
    int earthRadius = EARTH_RADIUS;

    double dLat = deg2rad(point.getLat() - this.lat);  // deg2rad below
    double dLon = deg2rad(point.getLng() - this.lng);

    double haversine = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(deg2rad(point.getLat())) * Math.cos(deg2rad(this.lat)) *
                    Math.sin(dLon/2) * Math.sin(dLon/2);

    double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
    double distance = earthRadius * c; // Distance in km
    return distance;
  }

  private double deg2rad(double deg) {
    return deg * (Math.PI/180);
  }

  public double getDistanceFromPoints(Point point) {
    return Math.sqrt(
            Math.pow(point.getLat() - this.lat, 2) +
                    Math.pow(point.getLng() - this.lng, 2)
    );
  }
}
