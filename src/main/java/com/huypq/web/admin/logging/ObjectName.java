package com.huypq.web.admin.logging;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectName {
    String name() default "";
}