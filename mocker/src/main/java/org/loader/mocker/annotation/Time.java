package org.loader.mocker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Appoint this field is time. It will return millis time when the field is long, or a formatted time when the field is string.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Time {

    /**
     * value is the format of time.
     * @return
     */
    String value() default "yyyy-MM-dd HH:mm:ss";
}
