package com.gigigo.orchextra.utils.mapper;

public interface MapperDelegateToModel<M, P> {
  M delegateToModel(P delegate);
}

