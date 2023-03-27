package com.example.task_management.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {

    private final LockService lockService;

    @Around("@annotation(com.example.task_management.aop.TeamLock) && args(taskId)")
    public Object aroundMethod(ProceedingJoinPoint pjp, String taskId) throws Throwable {
        lockService.lock(taskId);
        try{
            return pjp.proceed();
        } finally {
            lockService.unlock(taskId);
        }
    }
}
