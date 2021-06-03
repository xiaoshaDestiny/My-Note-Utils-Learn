package com.learn.boot.config2;

import java.lang.annotation.*;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-05-29 00:46
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {

    String color() default "";

    String ownerName() default "";
}
