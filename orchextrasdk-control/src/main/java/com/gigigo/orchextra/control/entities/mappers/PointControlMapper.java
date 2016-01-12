package com.gigigo.orchextra.control.entities.mappers;

import com.gigigo.orchextra.control.entities.PointControl;
import com.gigigo.orchextra.domain.entities.Point;

public class PointControlMapper implements Mapper<Point, PointControl> {

    @Override
    public PointControl modelToData(Point model) {
        PointControl data = new PointControl();

        data.setLat(model.getLat());
        data.setLng(model.getLng());

        return data;
    }

    @Override
    public Point dataToModel(PointControl data) {
        Point model = new Point();

        model.setLat(data.getLat());
        model.setLng(data.getLng());

        return model;
    }
}
