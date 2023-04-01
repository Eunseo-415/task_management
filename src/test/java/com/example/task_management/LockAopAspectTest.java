package com.example.task_management;

import com.example.task_management.aop.LockAopAspect;
import com.example.task_management.aop.LockService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LockAopAspectTest {
    @Mock
    private LockService lockService;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    private LockAopAspect lockAopAspect;

//    @Test
//    private void lockAndUnlock(){
//        //given
//        ArgumentCaptor<String> lockArgumentCaptor = ArgumentCaptor.forClass(String.class)
//        //when
//        lockAopAspect.aroundMethod(proceedingJoinPoint, taskId);
//        //then
//        verify(lockService, times(1)).lock();
//        verify(lockService, times(1)).unlock();
//
//    }
}
