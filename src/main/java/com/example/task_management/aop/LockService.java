package com.example.task_management.aop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedissonClient redissonClient;

    public void lock(String taskId){
        RLock lock = redissonClient.getLock(getLockKey(taskId));
        log.debug("Trying lock for taskId : {} ", taskId);

        try{
            boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
            if(!isLock){
                log.error("=============Lock acquisition failed===========");
                throw new RuntimeException("Lock acquisition failed");
            }
        } catch(Exception e){
            log.error("redis lock failed", e);
        }
    }

    public void unlock(String taskId){
        log.debug("Unlock for taskId : {} ", taskId);
        redissonClient.getLock(getLockKey(taskId)).unlock();
    }

    private static String getLockKey(String taskId) {
        return "TASKLK:" + taskId;
    }
}
