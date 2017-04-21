package com.adbourne.geo.readers.impl;

import com.adbourne.geo.domain.DbfField;
import com.adbourne.geo.domain.DbfFileMetadata;
import com.adbourne.geo.readers.DbfFileReader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import static com.adbourne.geo.util.FileChannelUtil.*;

@Slf4j
public class DefaultDbfFileReader implements DbfFileReader {

    public DbfFileMetadata readMetadata(File dbfFile) throws IOException {

        if (dbfFile.exists()) {

            try (FileChannel fileChannel = new FileInputStream(dbfFile).getChannel()) {
                // Skip 4 bytes
                fileChannel.read(byteBuffer(4, ByteOrder.LITTLE_ENDIAN));

                // Number of record
                int numberOfRecords = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);

                // Dbf header length
                short headerLength = readShort(fileChannel, ByteOrder.LITTLE_ENDIAN);

                // Dbf record length
                short recordLength = readShort(fileChannel, ByteOrder.LITTLE_ENDIAN);

                // Skip 8 bytes
                fileChannel.read(byteBuffer(8, ByteOrder.LITTLE_ENDIAN));

                int numberOfFields = ((Double) ((headerLength - 33) / 32.0)).intValue();
                DbfField[] fields = new DbfField[numberOfFields];
                for (int i = 0; i < numberOfFields; i++) {
                    fields[i] = readDbfField(fileChannel);
                }

                return DbfFileMetadata.builder()
                        .dbfFields(fields)
                        .dbfNumRecords(numberOfRecords)
                        .dbfHeaderLength(headerLength)
                        .dbfRecordLength(recordLength)
                        .build();
            }
        } else {
            throw new FileNotFoundException(String.format("File '%s' does not exist", dbfFile.getCanonicalPath()));
        }
    }


    private DbfField readDbfField(FileChannel fileChannel) throws IOException {
        String name = readString(fileChannel, ByteOrder.LITTLE_ENDIAN, 11);

        byte fieldType = readBytes(fileChannel, ByteOrder.LITTLE_ENDIAN, 1)[0];

        byte[] address = readBytes(fileChannel, ByteOrder.LITTLE_ENDIAN, 4);

        int size = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);

        int precision = readInt(fileChannel, ByteOrder.LITTLE_ENDIAN);

        byte[] padding = readBytes(fileChannel, ByteOrder.LITTLE_ENDIAN, 14);

        return DbfField.builder()
                .name(name)
                .fieldType(fieldType)
                .address(address)
                .size(size)
                .precision(precision)
                .padding(padding)
                .build();
    }

}
