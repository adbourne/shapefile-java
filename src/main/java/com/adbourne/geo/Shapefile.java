package com.adbourne.geo;

import com.vividsolutions.jts.geom.Geometry;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Shapefile<T> {

    public List<T> parse(File file) throws IOException;
}
