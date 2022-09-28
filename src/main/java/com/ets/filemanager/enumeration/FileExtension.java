package com.ets.filemanager.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileExtension {
    PNG  ( "png"  ),
    JPG  ( "jpg"  ),
    JPEG ( "jpeg" ),
    PDF  ( "pdf"  ),
    DOCX ( "docx" ),
    XLSX ( "xlsx" );

    private final String extension;

    public String getExtension() {
        return extension;
    }

    public static FileExtension fromExtension(String extension) {
        for (FileExtension fileExtension : FileExtension.values()) {
            if (fileExtension.getExtension().equalsIgnoreCase(extension)) {
                return fileExtension;
            }
        }
        return null;
    }
}
