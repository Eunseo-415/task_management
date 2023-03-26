package com.example.task_management.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TeamLock {
    long tryLockTime() default 5000L;
}
