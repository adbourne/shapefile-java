package com.adbourne.geo.readers;

import com.adbourne.geo.domain.GeometryType;
import com.adbourne.geo.domain.ShapefileMetadata;
import com.adbourne.geo.domain.geometry.BoundingBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.adbourne.geo.util.FileChannelUtil.byteBuffer;
import static com.adbourne.geo.util.FileChannelUtil.readDouble;
import static com.adbourne.geo.util.FileChannelUtil.readInt;

public abstract class AbstractShapefileReader<T> implements ShapefileReader<T> {

    @Override
    public ShapefileMetadata readMetadata(File shapefile) throws IOException {

        if (shapefile.exists()) {

            try (FileChannel fileChannel = new FileInputStream(shapefile).getChannel()) {
                return doReadMetadata(fileChannel);

            }
        } else {
            throw new FileNotFoundException(String.format("File '%s' does not exist", shapefile.getCanonicalPath()));
        }
    }

    private ShapefileMetadata doReadMetadata(FileChannel fileChannel) throws IOException {
        // Skip the first 24 bytes as not used
        fileChannel.read(byteBuffer(24, ByteOrder.BIG_ENDIAN));

        // File length
        int fileLength = readInt(fileChannel, ByteOrder.BIG_ENDIAN);

        // Skip 4 bytes
        fileChannel.read(byteBuffer(4, ByteOrder.LITTLE_ENDIAN));

        // Shape type
        int geometryTypeNumeric = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);
        GeometryType geometryType = GeometryType.fromNumeric(geometryTypeNumeric);

        // Bounding Box
        double bboxMinX = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMinY = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMaxX = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMaxY = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMinZ = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMaxZ = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMinM = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);
        double bboxMaxM = readDouble(fileChannel, ByteOrder.LITTLE_ENDIAN);

        return ShapefileMetadata.builder()
                .geometryType(geometryType)
                .boundingBox(BoundingBox.builder()
                        .minX(bboxMinX)
                        .minY(bboxMinY)
                        .maxX(bboxMaxX)
                        .maxY(bboxMaxY)
                        .minZ(bboxMinZ)
                        .maxZ(bboxMaxZ)
                        .minM(bboxMinM)
                        .maxM(bboxMaxM)
                        .build())
                .fileLength(fileLength)
                .build();
    }

    @Override
    public List<T> readShapeData(File shapefile) throws IOException {
        if (shapefile.exists()) {

            try (FileInputStream fileInputStream = new FileInputStream(shapefile)) {

                return readShapeData(fileInputStream);
            }

        } else {
            throw new FileNotFoundException(String.format("File '%s' does not exist", shapefile.getCanonicalPath()));
        }
    }

    @Override
    public List<T> readShapeData(FileInputStream fileInputStream) throws IOException {
        FileChannel fileChannel = fileInputStream.getChannel();

        ShapefileMetadata shapefileMetadata = doReadMetadata(fileChannel);

        List<T> geometries = new ArrayList<>();

        boolean hasNext = true;

        while (hasNext) {
            int recordNumber = readInt(fileChannel, ByteOrder.BIG_ENDIAN);
            int contentLength = readInt(fileChannel, ByteOrder.BIG_ENDIAN);

            if (contentLength != 0) {
                geometries.add(readShape(fileChannel));

            } else {
                hasNext = false;
            }
        }

        return geometries;
    }

    protected abstract T readShape(FileChannel fileChannel) throws IOException;

}
