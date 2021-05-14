package com.learn.boot.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-05-15 01:18
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class SimpleAspect {

    @Pointcut("@annotation(com.learn.boot.config.VipCheck)")
    public void vipCheckMethodPointcut() {}

    @Async
    @Around("vipCheckMethodPointcut()")
    public String beforeCall(ProceedingJoinPoint  joinPoint) throws Throwable {
        Object arg = joinPoint.getArgs()[0];
        Integer vipLevel = (Integer) arg;
        if (vipLevel <= 2) {
            return "sorry 您的vip等级太低";
        } else {
            return joinPoint.proceed().toString();
        }
    }
}
