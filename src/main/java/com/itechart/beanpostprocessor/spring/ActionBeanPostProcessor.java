package com.itechart.beanpostprocessor.spring;

import com.itechart.beanpostprocessor.BeanPostProcessorApplication;
import com.itechart.beanpostprocessor.MethodInvokeCache;
import com.itechart.beanpostprocessor.ToCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ActionBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var beanClass = bean.getClass();
        Collection<String> methodsWithCache = Arrays.stream(beanClass.getMethods())
                .filter(method -> method.isAnnotationPresent(ToCache.class))
                .map(Method::getName)
                .collect(Collectors.toList());
        if (!methodsWithCache.isEmpty()) {
            Object proxy = Proxy.newProxyInstance(
                    BeanPostProcessorApplication.class.getClassLoader(),
                    beanClass.getInterfaces(),
                    (o, method, objects) -> {
                        Object result = method.invoke(bean, objects);
                        if (methodsWithCache.contains(method.getName())) {
                            MethodInvokeCache.putToCache(objects);
                        }
                        return result;
                    }
            );
            return proxy;
        }
        return bean;
    }

}
