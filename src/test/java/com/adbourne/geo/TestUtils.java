package com.adbourne.geo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class TestUtils {

    public static File getFileFromResources(String resource) throws URISyntaxException, FileNotFoundException {
        File file = new File(
                TestUtils.class.getClassLoader().getResource(resource).toURI()
        );
        if (file.exists()) {
            return file;

        } else {
            throw new FileNotFoundException(String.format("File '%s' does not exist", resource));
        }
    }

}
