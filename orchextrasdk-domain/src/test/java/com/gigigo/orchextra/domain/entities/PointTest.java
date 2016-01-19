package com.gigigo.orchextra.domain.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldGetDistanceFromLatLonInKm() throws Exception {
        Point point1 = new Point();
        point1.setLat(40.4451852);
        point1.setLng(-3.6256787);

        Point point2 = new Point();
        point2.setLat(40.442284);
        point2.setLng(-3.6238204);

        double distance = point1.getDistanceFromPointInKm(point2);
        assertEquals(0.35, distance, 0.01);
    }

    @Test
    public void shouldGetDistanceFromLatLonInKm2() throws Exception {
        Point point1 = new Point();
        point1.setLat(40.4451852);
        point1.setLng(-3.6256787);

        Point point2 = new Point();
        point2.setLat(40.442127);
        point2.setLng(-3.623765);

        double distance = point1.getDistanceFromPointInKm(point2);
        assertEquals(0.37, distance, 0.01);
    }
}