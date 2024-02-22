package Gureum_World.server.domain.post.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class Aspects
{
    @Around("execution(* Gureum_World.server.domain.post..*(..))")

    public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        System.out.println("Execution Time for " + joinPoint.getSignature().toShortString() + ": " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }
}
