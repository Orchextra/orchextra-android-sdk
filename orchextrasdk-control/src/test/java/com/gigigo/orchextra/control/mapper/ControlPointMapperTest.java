package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.builders.ControlPointBuilder;
import com.gigigo.orchextra.control.builders.PointBuilder;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.domain.entities.Point;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControlPointMapperTest {

    @Test
    public void shouldMapPointToControlPoint() throws Exception {

        Point point = PointBuilder.Builder().build();

        ControlPointMapper controlPointMapper = new ControlPointMapper();
        ControlPoint controlPoint = controlPointMapper.modelToControl(point);

        assertEquals(PointBuilder.LAT, controlPoint.getLat(), 0.0001);
        assertEquals(PointBuilder.LNG, controlPoint.getLng(), 0.0001);
    }

    @Test
    public void shouldMapControlPointToPoint() throws Exception {

        ControlPoint controlPoint = ControlPointBuilder.Builder().build();

        ControlPointMapper controlPointMapper = new ControlPointMapper();
        Point point = controlPointMapper.controlToModel(controlPoint);

        assertEquals(ControlPointBuilder.LAT, point.getLat(), 0.0001);
        assertEquals(ControlPointBuilder.LNG, point.getLng(), 0.0001);
    }
}