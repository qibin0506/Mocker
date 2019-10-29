package org.loader.mocker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define the range of number for Number class or string, etc.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumberRange {

    float DEFAULT_FROM = 0;
    float DEFAULT_TO = 10;

    /**
     * number range start
     * @return
     */
    float from() default DEFAULT_FROM;

    /**
     * number range end
     * @return
     */
    float to() default DEFAULT_TO;
}
