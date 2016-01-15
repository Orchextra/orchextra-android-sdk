package com.gigigo.orchextra.utils.mapper;

public interface Mapper<M, P> {
  P modelToDelegate(M model);
  M delegateToModel(P delegate);
}

