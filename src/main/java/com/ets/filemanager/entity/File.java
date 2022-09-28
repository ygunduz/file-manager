package com.ets.filemanager.entity;

import com.ets.filemanager.enumeration.FileExtension;
import com.ets.filemanager.enumeration.converter.FileExtensionConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long size;

    @Enumerated(EnumType.STRING)
    @Convert(converter = FileExtensionConverter.class)
    private FileExtension extension;

    private String path;

    public File(String name, Long size, FileExtension extension, String path) {
        this.name = name;
        this.size = size;
        this.extension = extension;
        this.path = path;
    }
}
