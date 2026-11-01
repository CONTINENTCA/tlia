package com.zly.tliaswebmanagement.utils;

public class CurrentHolder {
    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrentId(Integer EmpId) {
        CURRENT_LOCAL.set(EmpId);
    }

    public static Integer GetCurrentId(){
        return CURRENT_LOCAL.get();
    }

    public static void removeCurrentId(){
        CURRENT_LOCAL.remove();
    }
}
