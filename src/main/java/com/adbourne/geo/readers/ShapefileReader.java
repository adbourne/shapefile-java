package com.adbourne.geo.readers;

import com.adbourne.geo.domain.ShapefileMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Reads a Shapefile (.shp) file
 */
public interface ShapefileReader<T> {

    public ShapefileMetadata readMetadata(File shapefile) throws IOException;

    /**
     * Reads shape data from a File
     * @param shapefile
     * @return
     * @throws IOException
     */
    public List<T> readShapeData(File shapefile) throws IOException;

    /**
     * Reads shape data from a FileInputStream, this method should NOT look
     * to close the FileInputStream once reading is complete, it is the responsibility
     * of the calling method.
     *
     * @param shapefile
     * @return
     * @throws IOException
     */
    public List<T> readShapeData(FileInputStream shapefile) throws IOException;

}
