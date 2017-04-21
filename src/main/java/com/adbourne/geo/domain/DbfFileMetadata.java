package com.adbourne.geo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Metadata read from a DBF file
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DbfFileMetadata {

    private File dbfFile;

    private DbfField[] dbfFields;

    private int dbfNumRecords;

    private short dbfHeaderLength;

    private short dbfRecordLength;


    /**
     * Returns the number of entries in the DBF table
     *
     * @return
     */
    public int attributeCount() {
        if (dbfFields != null) {
            return dbfFields.length;
        } else {
            return 0;
        }

    }

}
