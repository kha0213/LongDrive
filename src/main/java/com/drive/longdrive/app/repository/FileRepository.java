package com.drive.longdrive.app.repository;

import com.drive.longdrive.app.dto.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long>, FileCustomRepository {
    Optional<File> findBySizeEquals(Long size);
}
