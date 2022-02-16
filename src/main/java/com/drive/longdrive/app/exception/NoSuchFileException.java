package com.drive.longdrive.app.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NoSuchFileException extends Exception {
    public NoSuchFileException() {
    }

    public NoSuchFileException(Long fileId) {
        super("no such file exception!! file id : ["+ fileId +"]");
    }
    public NoSuchFileException(List<Long> fileIds) {
        super("no such file exception!! file ids : ["+ fileIds +"]");
    }
    public NoSuchFileException(String message) {
        super(message);
    }
}
