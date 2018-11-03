package com.bilev.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
    private static final Logger LOG = Logger.getLogger(LoggerAspect.class);

    @Before("execution(* com.bilev..*())")
    public void logBefore(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        LOG.debug("Call method " + methodName);
    }

    @Before("execution(* com.bilev..*(..))")
    public void logBeforeWithArgs(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        LOG.debug("Call method " + methodName + " with args " + methodArgs);
    }

    @AfterReturning(
            pointcut = "execution(* com.bilev..*(..))",
            returning= "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {

        LOG.debug("Method : " + joinPoint.getSignature().getName());
        LOG.debug("Returned value is : " + result);
    }

    @After("execution(void com.bilev..*(..))")
    public void logAfter(JoinPoint joinPoint) {

        LOG.debug("Method : " + joinPoint.getSignature().getName() + "completed");
    }

}
