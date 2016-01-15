package com.gigigo.orchextra.utils.mapper;

import java.util.ArrayList;
import java.util.List;

public class ListMapper<M, P> implements Mapper<List<M>, List<P>> {

  private Mapper<M, P> mapper;

  public ListMapper(Mapper<M, P> mapper) {
    this.mapper = mapper;
  }


  @Override public List<P> modelToDelegate(List<M> models) {
    ArrayList<P> persistences = new ArrayList<>();
    if (models != null && models.size() > 0) {
      for (M model : models) {
        persistences.add(mapper.modelToDelegate(model));
      }
    }
    return persistences;
  }

  @Override public List<M> delegateToModel(List<P> delegate) {
    ArrayList<M> models = new ArrayList<>();
    if (delegate != null && delegate.size() > 0) {
      for (P persistence : delegate) {
        models.add(mapper.delegateToModel(persistence));
      }
    }
    return models;
  }
}
