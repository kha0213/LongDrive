package com.drive.longdrive.app.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotExistFileException extends Exception {
    public NotExistFileException() {
    }

    public NotExistFileException(String message) {
        super(message);
    }
}
