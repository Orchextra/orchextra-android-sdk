package com.gigigo.orchextra.domain.model.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class URLFunctionalityImpl implements URLFunctionality {

  private final String url;

  public URLFunctionalityImpl(String url) {
    this.url = url;
  }

  @Override public boolean isSupported() {
    return (url==(null))? false : true;
  }

  @Override public String getUrl() {
    return url;
  }
}
