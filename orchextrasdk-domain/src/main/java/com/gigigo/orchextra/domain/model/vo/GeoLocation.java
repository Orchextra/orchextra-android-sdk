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

public class GeoLocation {

  private String country;
  private String countryCode;
  private String locality;
  private String zip;
  private String street;
  private OrchextraLocationPoint point;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public OrchextraLocationPoint getPoint() {
    return point;
  }

  public void setPoint(OrchextraLocationPoint point) {
    this.point = point;
  }

  public static GeoLocation createNullGeoLocationInstance() {
    GeoLocation geoLocation = new GeoLocation();
    geoLocation.country = "";
    geoLocation.countryCode = "";
    geoLocation.locality = "";
    geoLocation.street = "";
    geoLocation.zip = "";
    geoLocation.point = new OrchextraLocationPoint();
    geoLocation.point.setLat(0);
    geoLocation.point.setLng(0);
    return geoLocation;
  }
}
