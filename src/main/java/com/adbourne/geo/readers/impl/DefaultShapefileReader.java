package com.adbourne.geo.readers.impl;

import com.adbourne.geo.domain.GeometryType;
import com.adbourne.geo.readers.AbstractShapefileReader;
import com.vividsolutions.jts.geom.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import static com.adbourne.geo.util.FileChannelUtil.readDouble;
import static com.adbourne.geo.util.FileChannelUtil.readInt;

@Slf4j
public class DefaultShapefileReader extends AbstractShapefileReader<Geometry> {

    protected Geometry readShape(FileChannel fileChannel) throws IOException {
        int shapeType = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        GeometryType geometryType = GeometryType.fromNumeric(shapeType);
        GeometryFactory geometryFactory = new GeometryFactory();

        switch (geometryType) {

            case POINT:
                log.trace("Read a point");
                return readPoint(fileChannel, geometryFactory);

            case POLY_LINE:
                log.trace("Read a line");
                return readLine(fileChannel, geometryFactory);

            case POLYGON:
                log.trace("Read a polygon");
                return readPolygon(fileChannel, geometryFactory);

            default:
                log.warn("Geometry type '{}' is not supported", geometryType.name());
                return null;
        }

    }

    private Coordinate readCoordinate(FileChannel fileChannel) throws IOException {
        return new Coordinate(
                readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN),
                readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN)
        );
    }

    private Coordinate[] readCoordinates(FileChannel fileChannel, int numberOfCoords) throws IOException {
        Coordinate[] coordinates = new Coordinate[numberOfCoords];
        for (int i = 0; i < numberOfCoords; i++) {
            coordinates[i] = readCoordinate(fileChannel);
        }
        return coordinates;
    }

    private Point readPoint(FileChannel fileChannel, GeometryFactory geometryFactory) throws IOException {
        return geometryFactory.createPoint(readCoordinate(fileChannel));
    }

    private Geometry readLine(FileChannel fileChannel, GeometryFactory geometryFactory) throws IOException {
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double minX
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double minY
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double maxX
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double maxY

        int numberOfParts = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        int numberOfTotalPoints = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < numberOfParts; i++) { // Could create `int[] partIndexes` if ever needed
            readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        }

        return geometryFactory.createLineString(readCoordinates(fileChannel, numberOfTotalPoints));
    }

    private Polygon readPolygon(FileChannel fileChannel, GeometryFactory geometryFactory) throws IOException {
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double minX
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double minY
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double maxX
        readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN); // double maxY

        int numberOfParts = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        int numberOfTotalPoints = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < numberOfParts; i++) { // Could create `int[] partIndexes` if ever needed
            readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        }

        return geometryFactory.createPolygon(readCoordinates(fileChannel, numberOfTotalPoints));
    }
}
