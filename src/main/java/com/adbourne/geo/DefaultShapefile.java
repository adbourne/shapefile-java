package com.adbourne.geo;

import com.adbourne.geo.domain.DbfFileMetadata;
import com.adbourne.geo.domain.ShapefileMetadata;
import com.adbourne.geo.readers.DbfFileReader;
import com.adbourne.geo.readers.ShapefileReader;
import com.adbourne.geo.readers.impl.DefaultDbfFileReader;
import com.adbourne.geo.readers.impl.DefaultShapefileReader;
import com.vividsolutions.jts.geom.Geometry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class DefaultShapefile implements Shapefile<Geometry> {

    private final String DBF_FILE_EXTENSION = "dbf";

    private ShapefileReader shapefileReader;
    private DbfFileReader dbfFileReader;


    public DefaultShapefile(){
        this.shapefileReader = new DefaultShapefileReader();
        this.dbfFileReader = new DefaultDbfFileReader();
    }


    public List<Geometry> parse(File file) throws IOException {

        log.debug("Reading Shapefile at path '{}'", file.getCanonicalPath());
        this.shapefileReader.readMetadata(file); // ShapefileMetadata shapefileMetadata

        File dbfFile = inferDbfFilePath(file);
        log.debug("Reading DBF file at path '{}'", file.getCanonicalPath());
        this.dbfFileReader.readMetadata(dbfFile); // DbfFileMetadata dbfFileMetadata

        return this.shapefileReader.readShapeData(file);

    }

    private File inferDbfFilePath(final File mainFile) throws IOException {
        String dbfFilePath = FilenameUtils.getFullPath(mainFile.getCanonicalPath()) + FilenameUtils.removeExtension(mainFile.getName()) + "." + DBF_FILE_EXTENSION;
        log.info(dbfFilePath);
        return new File(dbfFilePath);
    }

}
