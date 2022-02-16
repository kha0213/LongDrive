package com.drive.longdrive.app.dto.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = FileExtensionValidator.class)
@Documented
public @interface FileExtension {
    String message() default "파일 확장자 에러입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
