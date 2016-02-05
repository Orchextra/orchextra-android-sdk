package com.gigigo.orchextra.domain.mappers;


/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public interface Mapper<M, A> {
  A modelToAndroid(M model);
  M androidToModel(A android);
}
