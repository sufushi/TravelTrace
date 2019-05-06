package com.rdc.project.traveltrace.model.query;

import java.util.concurrent.ConcurrentHashMap;

public class PagerUtil {

    private static ConcurrentHashMap<Class, Pager> sPagerMap;

    private static class PagerHolder {
        private static final PagerUtil INSTANCE = new PagerUtil();
    }

    private PagerUtil() {
        sPagerMap = new ConcurrentHashMap<>();
    }

    public static PagerUtil getInstance() {
        return PagerHolder.INSTANCE;
    }

    public void registerPager(Class clazz) {
        sPagerMap.put(clazz, new Pager());
    }

    public Pager getPager(Class clazz) {
        return sPagerMap.get(clazz);
    }
}
