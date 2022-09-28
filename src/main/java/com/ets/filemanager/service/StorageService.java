package com.ets.filemanager.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    Resource save(MultipartFile file) throws Exception;
    Resource load(String filename) throws Exception;
    void delete(String filename) throws Exception;
}
