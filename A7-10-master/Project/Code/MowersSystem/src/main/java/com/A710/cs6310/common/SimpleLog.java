package com.a710.cs6310.common;

public interface SimpleLog {
    boolean FLAG = false;

    static void log(String str) {
        if (FLAG) {
            System.out.println(str);
        }
    }
}
