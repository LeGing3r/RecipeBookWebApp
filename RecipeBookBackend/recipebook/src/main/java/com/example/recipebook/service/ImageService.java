package com.example.recipebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    private final String projectAbsolutePath;
    @Value("${images.source:null}")
    private String imagesSource;

    public ImageService() {
        projectAbsolutePath = Paths.get("").toAbsolutePath().toString();
    }

    String saveImage(byte[] bytes, String id) throws IOException {
        var imageName = imagesSource + id + ".png";
        var path = Path.of(projectAbsolutePath + imageName);

        Files.write(path, bytes);

        return id + ".png";
    }

    byte[] loadImage(String fileLocation) throws IOException {

        var path = Path.of(projectAbsolutePath + imagesSource + fileLocation);

        return Files.readAllBytes(path);
    }
}
