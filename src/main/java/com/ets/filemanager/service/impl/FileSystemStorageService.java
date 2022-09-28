package com.ets.filemanager.service.impl;

import com.ets.filemanager.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@Primary
public class FileSystemStorageService implements StorageService {
    private final Path root = Paths.get("uploads");

    @PostConstruct
    public void init() throws IOException {
        // initialize root folder if not exists
        if(!Files.exists(root)) {
            Files.createDirectory(root);
        }
        log.info("Storage Service initialized");
    }

    @Override
    public Resource save(MultipartFile file) throws RuntimeException {
        if(file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if(Files.exists(root.resolve(file.getOriginalFilename()))) {
            throw new RuntimeException("File with name " + file.getOriginalFilename() + " already exists");
        }
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            log.info("File saved successfully : {}" , file.getOriginalFilename());
            return load(file.getOriginalFilename());
        } catch (Exception e) {
            log.error("Could not save the file. Error: {}" , e.getMessage());
            throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) throws RuntimeException {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Could not read the file : {}" , filename);
                throw new RuntimeException("File not found: " + filename);
            }
        } catch (MalformedURLException e) {
            log.error("Error ocurred while reading file : {} , error : {} " , filename, e.getMessage());
            throw new RuntimeException("Error while reading file : " + e.getMessage());
        }
    }

    @Override
    public void delete(String filename) throws RuntimeException {
        try {
            Files.delete(root.resolve(filename));
            log.info("File deleted successfully : {}" , filename);
        } catch (IOException e) {
            log.error("Could not delete the file. File : {}, Error: {}", filename, e.getMessage());
            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
        }
    }
}
