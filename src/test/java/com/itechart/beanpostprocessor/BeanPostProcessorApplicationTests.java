package com.itechart.beanpostprocessor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeanPostProcessorApplicationTests {

    @Autowired
    private ServiceInterface service;

    @Test
    void contextLoads() {

        Assertions.assertEquals(0, MethodInvokeCache.getSize());

        service.doAction("some argument");

        Assertions.assertEquals(1, MethodInvokeCache.getSize());

        service.doAction("some argument2");

        Assertions.assertEquals(2, MethodInvokeCache.getSize());

        service.doAnother("some argument3");

        Assertions.assertEquals(2, MethodInvokeCache.getSize());
    }

}
