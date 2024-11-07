package com.example.booking.service;

import com.example.booking.entity.Room;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    @Value("${application.file.uploads.photos-output-path}")
    private String fileUplodpath;

    public String saveFile(@Nonnull MultipartFile sourceFile,
                           @Nonnull Room r,
                           @Nonnull String name) {
        final String fileUploadSubPath = "owner"+ File.separator + name;// per ogni ownwer
        return uploadFile(sourceFile,fileUploadSubPath);
    }

    private String uploadFile(@Nonnull MultipartFile sourceFile,
                              @Nonnull String fileUploadSubPath) {

        final String finalUploadPath = fileUplodpath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean cartellaCreata = targetFolder.mkdirs();
            if(!cartellaCreata){
                return null;
            }
        }
        final String extension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath+ File.separator + System.currentTimeMillis()+"."+extension;//una spece di id
        Path targetPath = Paths.get(targetFilePath);
        try{
            Files.write(targetPath, sourceFile.getBytes());
            return targetFilePath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String fileName) {
        if(fileName == null || fileName.isEmpty())
            return null;
        int ultimoPunto = fileName.lastIndexOf(".");
        if(ultimoPunto == -1)
            return null;
        return fileName.substring(ultimoPunto+1).toLowerCase();
    }
}
