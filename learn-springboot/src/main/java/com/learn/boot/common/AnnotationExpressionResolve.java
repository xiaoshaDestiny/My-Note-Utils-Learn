package com.learn.boot.common;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author xu.rb
 * @since 2021-06-03 21:24
 */
public class AnnotationExpressionResolve {

    /**
     * expression  ${person.getAddress().getHouse().getHouseName()}
     * 切面获取到的Method
     * 切面获取到的全部入参
     */
    public static String getExpressionResult(String expression, Method method, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        expression = expression.substring(2, expression.length() -1);
        String[] opArray = expression.split("\\.");
        Object result = null;
        if (opArray.length == 1) {
            result = getObjectValue(opArray[0], method, args);
        } else {
            Object objectValue = getObjectValue(opArray[0], method, args);
            for (int i = 1; i < opArray.length; i++) {
                String methodName = opArray[i].substring(0, opArray[i].length() - 2);
                objectValue = MethodUtils.invokeMethod(objectValue, methodName, (Object[]) null);
            }
            result = objectValue.toString();
        }
        return Objects.nonNull(result) ? result.toString() : null;
    }

    private static Object getObjectValue(String objectName, Method method, Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        if (parameterAnnotations != null && parameterAnnotations.length != 0) {
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] lineAnnotations = parameterAnnotations[i];
                int columnSize = lineAnnotations.length;
                for (int var = 0; var < columnSize; var++) {
                    Annotation annotation = lineAnnotations[var];
                    if (annotation instanceof ExpressionObject) {
                        ExpressionObject param = (ExpressionObject)annotation;
                        if (param.value().equals(objectName)) {
                            return args[i];
                        }
                    }
                }
            }
            throw new IllegalArgumentException(String.format("Params of Method %s has not 'ExpressionObject' annotation ewuals %s", method.getName(), objectName));
        } else {
            throw new IllegalArgumentException(String.format("Params of Method %s has not 'ExpressionObject' annotation ewuals %s", method.getName(), objectName));
        }
    }
}
