package com.gigigo.orchextra.domain.model.vo;


/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class GeoLocation {

  private String country;
  private String countryCode;
  private String locality;
  private String zip;
  private String street;
  private OrchextraPoint point;

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

  public OrchextraPoint getPoint() {
    return point;
  }

  public void setPoint(OrchextraPoint point) {
    this.point = point;
  }

  public static GeoLocation createNullGeoLocationInstance(){
    GeoLocation geoLocation = new GeoLocation();
    geoLocation.country = "";
    geoLocation.countryCode = "";
    geoLocation.locality = "";
    geoLocation.street = "";
    geoLocation.zip = "";
    geoLocation.point = new OrchextraPoint();
    geoLocation.point.setLat(0);
    geoLocation.point.setLng(0);
    return geoLocation;
  }
}
