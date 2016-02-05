package com.gigigo.orchextra.domain.mappers;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public interface MapperAndroidListToModelList<A, M> {
  List<M> androidListToModelList(List<A> androidList);
}
