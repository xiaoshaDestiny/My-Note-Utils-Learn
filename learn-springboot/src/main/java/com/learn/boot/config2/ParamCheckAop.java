package com.learn.boot.config2;

import com.learn.boot.common.AnnotationExpressionResolve;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xu.rb
 * @description: TODO
 * @since 2021-05-29 00:47
 */
@Aspect
@Component
public class ParamCheckAop {

    @Pointcut("@annotation(com.learn.boot.config2.ParamCheck)")
    public void paramCheckPoint() {}

    @Around("paramCheckPoint()")
    public String doAop(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ParamCheck paramCheck = method.getAnnotation(ParamCheck.class);

        String color = AnnotationExpressionResolve.getExpressionResult(paramCheck.color(), method, joinPoint.getArgs());
        String ownerNme = AnnotationExpressionResolve.getExpressionResult(paramCheck.ownerName(), method, joinPoint.getArgs());

        if ("black".equalsIgnoreCase(color)) {
            return String.format("发现黑色盒子, owner is %s",ownerNme);
        } else {
            return joinPoint.proceed().toString();
        }
    }
}
