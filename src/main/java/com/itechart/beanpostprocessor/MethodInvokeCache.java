package com.itechart.beanpostprocessor;

import java.util.HashSet;
import java.util.Set;

public class MethodInvokeCache {

    private static final Set<Object> cache = new HashSet<>();

    public static void putToCache(Object result) {
        cache.add(result);
    }

    public static int getSize() {
        return cache.size();
    }
}
