package com.example.booking.Dto;

import io.micrometer.common.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        try {
            Path filePath = new File(fileUrl).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
