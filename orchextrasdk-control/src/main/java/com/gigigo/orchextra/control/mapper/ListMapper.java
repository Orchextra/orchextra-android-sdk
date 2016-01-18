package com.gigigo.orchextra.control.mapper;

import java.util.ArrayList;
import java.util.List;

public class ListMapper<M, P> implements Mapper<List<M>, List<P>> {

  private Mapper<M, P> mapper;

  public ListMapper(Mapper<M, P> mapper) {
    this.mapper = mapper;
  }


  @Override public List<P> modelToControl(List<M> models) {
    ArrayList<P> persistences = new ArrayList<>();
    if (models != null && models.size() > 0) {
      for (M model : models) {
        persistences.add(mapper.modelToControl(model));
      }
    }
    return persistences;
  }

  @Override public List<M> controlToModel(List<P> control) {
    ArrayList<M> models = new ArrayList<>();
    if (control != null && control.size() > 0) {
      for (P persistence : control) {
        models.add(mapper.controlToModel(persistence));
      }
    }
    return models;
  }
}
