package com.adbourne.geo.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Utility methods used to read from a FileChannel
 */
public class FileChannelUtil {

    public static byte[] readBytes(FileChannel fileChannel, ByteOrder byteOrder, int numberOfBytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(numberOfBytes);
        byteBuffer.order(byteOrder);
        fileChannel.read(byteBuffer);
        return byteBuffer.array();
    }

    public static String readString(FileChannel fileChannel, ByteOrder byteOrder, int numberOfBytes) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(numberOfBytes);
        byteBuffer.order(byteOrder);
        fileChannel.read(byteBuffer);
        return byteBuffer.toString();
    }

    public static int readInt(FileChannel fileChannel, ByteOrder byteOrder) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(byteOrder);
        fileChannel.read(byteBuffer);
        return byteBuffer.getInt(0);
    }

    public static short readShort(FileChannel fileChannel, ByteOrder byteOrder) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(byteOrder);
        fileChannel.read(byteBuffer);
        return byteBuffer.getShort(0);
    }

    public static double readDouble(FileChannel fileChannel, ByteOrder byteOrder) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(byteOrder);
        fileChannel.read(byteBuffer);
        return byteBuffer.getDouble(0);
    }

    public static ByteBuffer byteBuffer(final int length, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.order(byteOrder);
        return byteBuffer;
    }

}
