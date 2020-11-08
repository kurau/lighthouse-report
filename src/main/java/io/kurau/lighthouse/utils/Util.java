package io.kurau.lighthouse.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    public static String getResourceAsString(String path) {
        try {
            String result = IOUtils.toString(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8.name());
            return result;
        } catch (IOException var3) {
            throw new RuntimeException("can't read path " + path, var3);
        }
    }

    public static long toLong(String value) {
        return Double.valueOf(value).longValue();
    }
}
