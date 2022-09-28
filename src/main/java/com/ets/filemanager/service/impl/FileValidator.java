package com.ets.filemanager.service.impl;

import com.ets.filemanager.enumeration.FileExtension;
import com.ets.filemanager.exception.BusinessException;
import com.ets.filemanager.service.Validator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;

@Component
public class FileValidator implements Validator<MultipartFile> {
    private static final double BYTE_TO_MB_MULTIPLIER = 0.000001;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(Arrays.stream(FileExtension.values()).map(FileExtension::getExtension).toArray(String[]::new));

    @Override
    public void validate(MultipartFile multipartFile) throws BusinessException {
        StringJoiner errorMessage = new StringJoiner("\n");

        long sizeInByte = multipartFile.getSize();
        double mb = sizeInByte * BYTE_TO_MB_MULTIPLIER;
        int compare = Double.compare(mb, 5.0);
        if (compare > 0) {
            errorMessage.add("File size is too big. Max size is 5MB");
        }
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(!ALLOWED_EXTENSIONS.contains(extension)) {
            errorMessage.add("File extension is not allowed. Allowed extensions are : " + String.join(", ", ALLOWED_EXTENSIONS));
        }



        if(errorMessage.length() > 0) {
            throw new BusinessException(errorMessage.toString());
        }
    }
}
