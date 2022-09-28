package com.ets.filemanager.util;

import com.ets.filemanager.enumeration.FileExtension;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtils {
    private FileUtils() {}

    public static FileExtension getExtension(MultipartFile file) {
        return FileExtension.fromExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
    }

    public static String getName(MultipartFile file) {
        return FilenameUtils.getBaseName(file.getOriginalFilename());
    }

    public static Pair<String, FileExtension> getFileNameAndExtension(MultipartFile file) {
        return Pair.of(getName(file), getExtension(file));
    }
}
