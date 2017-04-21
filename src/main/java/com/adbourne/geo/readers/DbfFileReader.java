package com.adbourne.geo.readers;

import com.adbourne.geo.domain.DbfFileMetadata;

import java.io.File;
import java.io.IOException;

/**
 * Reads a DBF (*.dbf) file
 */
public interface DbfFileReader {

    public DbfFileMetadata readMetadata(File dbfFile) throws IOException;

}
