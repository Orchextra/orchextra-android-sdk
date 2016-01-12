package com.gigigo.orchextra.control.entities.mappers;

import java.util.ArrayList;
import java.util.List;

public class ListMapper<Model, Data> implements Mapper<List<Model>, List<Data>>{

    private final Mapper mapper;

    public ListMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Data> modelToData(List<Model> modelList) {
        List<Data> dataList = new ArrayList<>();

        for (Model model : modelList) {
            Data data = (Data) mapper.modelToData(model);
            dataList.add(data);
        }

        return dataList;
    }

    @Override
    public List<Model> dataToModel(List<Data> dataList) {
        List<Model> modelList = new ArrayList<>();

        for (Data data : dataList) {
            Model model = (Model) mapper.dataToModel(data);
            modelList.add(model);
        }

        return modelList;
    }
}
