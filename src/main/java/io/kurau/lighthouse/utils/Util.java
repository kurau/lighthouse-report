package io.kurau.lighthouse.utils;

import io.kurau.lighthouse.beans.LightHouseResult;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;

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

    public static boolean isLong(Function<LightHouseResult, String> function, LightHouseResult result) {
        try {
            Optional<String> lhr = Optional.ofNullable(function.apply(result));
            if (lhr.isPresent()) {
                Double.valueOf(lhr.get()).longValue();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
