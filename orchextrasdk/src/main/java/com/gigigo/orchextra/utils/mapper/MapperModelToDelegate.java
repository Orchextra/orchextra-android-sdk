package com.gigigo.orchextra.utils.mapper;

public interface MapperModelToDelegate<M, P> {
  P modelToDelegate(M model);
}

