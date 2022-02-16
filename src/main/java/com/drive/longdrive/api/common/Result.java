package com.drive.longdrive.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 공통 리턴하는 클래스이다.
 */
@AllArgsConstructor
@Data
public class Result <T> {
    private T data;
}
