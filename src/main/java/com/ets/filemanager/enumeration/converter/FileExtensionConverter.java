package com.ets.filemanager.enumeration.converter;

import com.ets.filemanager.enumeration.FileExtension;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FileExtensionConverter implements AttributeConverter<FileExtension, String> {

    @Override
    public String convertToDatabaseColumn(FileExtension attribute) {
        return attribute.getExtension();
    }

    @Override
    public FileExtension convertToEntityAttribute(String dbData) {
        return FileExtension.fromExtension(dbData);
    }

}
