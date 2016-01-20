package com.gigigo.orchextra.control.mapper;

public interface MapperControlToModel<M, P> {
  M controlToModel(P control);
}

