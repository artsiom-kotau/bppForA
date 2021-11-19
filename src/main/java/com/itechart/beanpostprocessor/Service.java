package com.itechart.beanpostprocessor;

import org.springframework.stereotype.Component;

@Component
public class Service implements ServiceInterface{

    @ToCache
    public void doAction(Object argument) {

    }

    public void doAnother(Object argument) {

    }
}
