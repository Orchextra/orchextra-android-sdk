package com.gigigo.orchextra.domain;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/5/16.
 */
public interface Validator<T> {
  void doValidate(T object);
}
