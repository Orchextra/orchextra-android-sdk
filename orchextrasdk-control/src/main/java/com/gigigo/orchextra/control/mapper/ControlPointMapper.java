package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.domain.entities.Point;

public class ControlPointMapper implements Mapper<Point, ControlPoint> {

    @Override
    public ControlPoint modelToControl(Point model) {
        ControlPoint controlPoint = new ControlPoint();

        controlPoint.setLat(model.getLat());
        controlPoint.setLng(model.getLng());

        return controlPoint;
    }

    @Override
    public Point controlToModel(ControlPoint control) {
        Point point = new Point();

        point.setLat(control.getLat());
        point.setLng(control.getLng());

        return point;
    }
}
