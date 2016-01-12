package com.gigigo.orchextra.control.entities.mappers;

public interface Mapper<Model, Data> {
    Data modelToData(Model data);
    Model dataToModel(Data data);
}
