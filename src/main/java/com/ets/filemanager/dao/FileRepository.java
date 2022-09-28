package com.ets.filemanager.dao;

import com.ets.filemanager.entity.File;
import com.ets.filemanager.enumeration.FileExtension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    long countByNameAndExtension(String name, FileExtension extension);
    long countByNameAndExtensionAndIdNot(String name, FileExtension extension, Long id);
}
