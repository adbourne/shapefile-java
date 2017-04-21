package com.adbourne.geo.domain;

import lombok.Getter;

public enum GeometryType {
    NULL_SHAPE(0),
    POINT(1),
    POLY_LINE(3),
    POLYGON(5),
    MULTI_POINT(8),
    POINT_Z(11),
    POLY_LINE_Z(13),
    POLYGON_Z(15),
    MULTI_POINT_Z(18),
    POINT_M(21),
    POLY_LINE_M(23),
    POLYGON_M(25),
    MULTI_POINT_M(28),
    MULTI_PATCH(31);

    @Getter
    private int value;

    GeometryType(int value) {
        this.value = value;
    }

    public static GeometryType fromNumeric(int value) {
        switch (value) {
            case 0:
                return NULL_SHAPE;

            case 1:
                return POINT;

            case 3:
                return POLY_LINE;

            case 5:
                return POLYGON;

            case 8:
                return MULTI_POINT;

            case 11:
                return POINT_Z;

            case 13:
                return POLY_LINE_Z;

            case 15:
                return POLYGON_Z;

            case 18:
                return MULTI_POINT_Z;

            case 21:
                return POINT_M;

            case 23:
                return POLY_LINE_M;

            case 25:
                return POLYGON_M;

            case 28:
                return MULTI_POINT_M;

            case 31:
                return MULTI_PATCH;

            default:
                return null;
        }
    }
}
