package com.learn.boot.common;

import java.lang.annotation.*;

/**
 * @author xu.rb
 * @since 2021-06-03 21:31
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpressionObject {
    String value();
}
