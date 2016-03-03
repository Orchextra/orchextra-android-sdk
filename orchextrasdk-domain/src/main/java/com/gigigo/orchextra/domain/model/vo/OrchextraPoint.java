/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.model.vo;


public class OrchextraPoint {

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

  public double getDistanceFromPointInKm(OrchextraPoint point) {
    int earthRadius = EARTH_RADIUS;

    double dLat = deg2rad(point.getLat() - this.lat);  // deg2rad below
    double dLon = deg2rad(point.getLng() - this.lng);

    double haversine = Math.sin(dLat / 2)
        * Math.sin(dLat / 2)
        + Math.cos(deg2rad(point.getLat()))
        * Math.cos(deg2rad(this.lat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

    double c = 2 * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
    double distance = earthRadius * c; // Distance in km
    return distance;
  }

  private double deg2rad(double deg) {
    return deg * (Math.PI / 180);
  }
}
