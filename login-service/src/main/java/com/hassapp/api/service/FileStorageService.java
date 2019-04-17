package com.hassapp.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileStorageService {
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
    private final Path rootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file, String filename){
        try {
            System.out.println(filename);
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public Resource loadFile(String filename){
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("FAİL !");
            }
        }catch (Exception e){
            throw new RuntimeException("FAİL !");
        }
    }

    public void deleteFile(String fileName) throws IOException {
        FileSystemUtils.deleteRecursively(rootLocation.resolve(fileName));
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    public String createImageName(String nickname, String originalFileName){
        String[] filenames = nickname.split("-");
        String[] originalNames = originalFileName.split("\\.");
        String filename = filenames[1] + "-photo" + "." + originalNames[1];
        System.out.println(filename);
        return filename;
    }
}
