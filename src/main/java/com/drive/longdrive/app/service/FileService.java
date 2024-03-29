package com.drive.longdrive.app.service;

import com.drive.longdrive.app.dto.FileSearch;
import com.drive.longdrive.app.dto.FileVO;
import com.drive.longdrive.app.dto.entity.File;
import com.drive.longdrive.app.exception.NoSuchFileException;
import com.drive.longdrive.app.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FileService {
    private final FileRepository fileRepository;

    private final FileStore fileStore;

    /**
     * 파일을 실제로 저장 후 DB 에도 저장
     * @param uploadFile controller에서 넘어온 MultipartFile 리스트
     * @return List<Long> 저장된 파일의 키값들
     * @throws IOException IO에러
     */
    public Long upload(MultipartFile uploadFile) throws IOException {
        java.io.File storeFile = fileStore.storeFile(uploadFile);
        File file = fileRepository.save(new File(storeFile));
        return file.getId();
    }

    /**
     * 파일을 실제로 저장 후 DB 에도 저장
     * @param uploadFiles controller에서 넘어온 MultipartFile 리스트
     * @return List<Long> 저장된 파일의 키값들
     * @throws IOException IO에러
     */
    public List<Long> upload(List<MultipartFile> uploadFiles) throws IOException {
        List<java.io.File> storeFiles = fileStore.storeFiles(uploadFiles);

        List<File> files = fileRepository.saveAll(storeFiles.stream()
                                                            .map(File::new)
                                                            .collect(toList()));
        return files.stream().map(File::getId).collect(toList());
    }

    /**
     * 파일을 실제로 삭제 후 DB 에도 삭제
     * @param fileId file의 pk
     */
    public void delete(Long fileId) throws NoSuchFileException {
        log.debug("deleteFile fileId [{}] ", fileId);
        Optional<File> file = fileRepository.findById(fileId);

        if(file.isEmpty()) {
            throw new NoSuchFileException(fileId);
        }

        fileStore.deleteFile(file.get().getRealFile());
        fileRepository.deleteById(fileId);
    }

    /**
     * 파일을 실제로 삭제 후 DB 에도 삭제
     * @param fileIds file의 pk list
     */
    public void deleteFiles(@NotEmpty List<Long> fileIds) throws NoSuchFileException {
        log.debug("deleteFiles fileIds [{}] ", fileIds);
        List<File> files = fileRepository.findAllById(fileIds);

        fileStore.deleteFiles(
                files.stream()
                     .map(File::getRealFile)
                     .collect(toList()));
        fileRepository.deleteAllById(fileIds);
    }

    /**
     * 파일 Id 리스트로 파일 찾기
     * @param search 파일 검색 객체
     * @return FileVO 리스트
     */
    public Page<FileVO> findFiles(FileSearch search, Pageable pageable) {
        Page<File> files = fileRepository.findFiles(search, pageable);
        return files.map(FileVO::new);
    }
}

