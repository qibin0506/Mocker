package org.loader.mocker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For string field, define the type of string.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Lang {

    /**
     * Chinese
     */
    String ZH_CN = "zh_cn";

    /**
     * upper English
     */
    String EN_US_UPPER = "en_us_upper";

    /**
     * lower English
     */
    String EN_US_LOWER = "en_us_lower";

    String value() default EN_US_LOWER;
}
