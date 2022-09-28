package com.ets.filemanager.dto;

import com.ets.filemanager.enumeration.FileExtension;
import lombok.Data;

import java.io.Serializable;

@Data
public class FileResponseDto implements Serializable {
    private Long id;
    private String name;
    private String path;
    private FileExtension extension;
    private Long size;
}
