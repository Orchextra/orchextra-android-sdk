package com.gigigo.orchextra.control.mapper;

public interface Mapper<M, P> {
  P modelToControl(M model);
  M controlToModel(P control);
}

