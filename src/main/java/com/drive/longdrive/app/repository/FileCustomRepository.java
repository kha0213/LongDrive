package com.drive.longdrive.app.repository;

import com.drive.longdrive.app.dto.FileSearch;
import com.drive.longdrive.app.dto.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FileCustomRepository {
    Page<File> findFiles(FileSearch search, Pageable pageable);
}
