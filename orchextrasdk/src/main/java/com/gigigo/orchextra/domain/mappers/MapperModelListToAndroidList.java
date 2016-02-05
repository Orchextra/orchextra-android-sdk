package com.gigigo.orchextra.domain.mappers;

import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public interface MapperModelListToAndroidList<M, A> {
  List<A> modelListToAndroidList(List<M> modelList);
}
