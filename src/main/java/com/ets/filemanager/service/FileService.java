package com.ets.filemanager.service;

import com.ets.filemanager.converter.EntityConverter;
import com.ets.filemanager.dao.FileRepository;
import com.ets.filemanager.dto.FileResponseDto;
import com.ets.filemanager.entity.File;
import com.ets.filemanager.enumeration.FileExtension;
import com.ets.filemanager.exception.BusinessException;
import com.ets.filemanager.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final StorageService storageService;
    private final FileRepository fileRepository;

    private final Validator<MultipartFile> fileValidator;

    private final EntityConverter<File, FileResponseDto> fileConverter;

    public List<FileResponseDto> list() {
        return fileConverter.toDto(fileRepository.findAll());
    }

    public byte[] readFile(Long id) throws BusinessException {
        File file = fileRepository.findById(id).orElseThrow(() -> new BusinessException("File not found"));
        try {
            Resource resource = storageService.load(file.getPath());
            return resource.getInputStream().readAllBytes();
        } catch (Exception e) {
            throw new BusinessException("File not found in storage");
        }
    }

    public FileResponseDto get(Long id) throws BusinessException {
        File file = fileRepository.findById(id).orElseThrow(() -> new BusinessException("File not found"));
        return fileConverter.toDto(file);
    }

    public FileResponseDto save(MultipartFile multipartFile) throws BusinessException {
        fileValidator.validate(multipartFile);
        Pair<String, FileExtension> fileNameAndExtension = FileUtils.getFileNameAndExtension(multipartFile);
        String name = fileNameAndExtension.getFirst();
        FileExtension extension = fileNameAndExtension.getSecond();
        long count = fileRepository.countByNameAndExtension(name, extension);
        if ( count > 0) {
            throw new BusinessException("File with name " + name + " and extension " + extension + " already exists");
        }
        try {
            Resource savedFile = storageService.save(multipartFile);
            String path = savedFile.getURL().getPath();
            File file = new File(name, multipartFile.getSize(), extension, path);
            file = fileRepository.save(file);
            return fileConverter.toDto(file);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public FileResponseDto update(Long id, MultipartFile multipartFile) throws BusinessException {
        fileValidator.validate(multipartFile);
        File file = fileRepository.findById(id).orElseThrow(() -> new BusinessException("File with id " + id + " not found"));
        Pair<String, FileExtension> fileNameAndExtension = FileUtils.getFileNameAndExtension(multipartFile);
        String name = fileNameAndExtension.getFirst();
        FileExtension extension = fileNameAndExtension.getSecond();
        long count = fileRepository.countByNameAndExtensionAndIdNot(name, extension, id);
        if(count > 0) {
            throw new BusinessException("File with name " + name + " and extension " + extension + " already exists");
        }
        try {
            storageService.delete(file.getName() + "." + file.getExtension());
            Resource savedFile = storageService.save(multipartFile);
            String path = savedFile.getURL().getPath();
            file.setName(name);
            file.setExtension(extension);
            file.setPath(path);
            file.setSize(multipartFile.getSize());
            file = fileRepository.save(file);
            return fileConverter.toDto(file);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void delete(Long id) throws BusinessException {
        File file = fileRepository.findById(id).orElseThrow(() -> new BusinessException("File with id " + id + " not found"));
        try {
            storageService.delete(file.getName() + "." + file.getExtension());
            fileRepository.delete(file);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
