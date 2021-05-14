package com.learn.boot.config;

import java.lang.annotation.*;

/**
 * @author xu.rb
 * @since 2021-05-15 01:06
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VipCheck {

}
