package com.ets.filemanager.converter;

import com.ets.filemanager.dto.FileResponseDto;
import com.ets.filemanager.entity.File;
import org.springframework.stereotype.Component;

@Component
public class FileEntityConverter implements EntityConverter<File, FileResponseDto> {

    @Override
    public File toEntity(FileResponseDto dto) {
        File file = new File();
        file.setId(dto.getId());
        file.setName(dto.getName());
        file.setSize(dto.getSize());
        file.setExtension(dto.getExtension());
        file.setPath(dto.getPath());
        return file;
    }

    @Override
    public FileResponseDto toDto(File entity) {
        FileResponseDto fileResponseDto = new FileResponseDto();
        fileResponseDto.setId(entity.getId());
        fileResponseDto.setName(entity.getName());
        fileResponseDto.setPath(entity.getPath());
        fileResponseDto.setExtension(entity.getExtension());
        fileResponseDto.setSize(entity.getSize());
        return fileResponseDto;
    }
}
