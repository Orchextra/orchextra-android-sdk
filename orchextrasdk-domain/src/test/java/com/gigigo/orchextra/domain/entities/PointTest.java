package com.gigigo.orchextra.domain.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {

    private Point point1;
    private Point point2;

    @Before
    public void setUp() throws Exception {
        point1 = new Point();
        point1.setLat(40.4451852);
        point1.setLng(-3.6256787);

        point2 = new Point();
        point2.setLat(40.442284);
        point2.setLng(-3.6238204);
    }

    @Test
    public void shouldGetDistanceFromLatLonInKm() throws Exception {
        double distance = point1.getDistanceFromLatLonInKm(point2);

        assertEquals(0, distance, 0.0001);
    }
}