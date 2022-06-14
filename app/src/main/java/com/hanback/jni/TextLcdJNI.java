package com.hanback.jni;

public class TextLcdJNI {
    static {
        System.loadLibrary("sloppy");
    }

    public native void on();
    public native void off();
    public native void initialize();
    public native void clear();

    public native void print1Line(String str);
    public native void print2Line(String str);
}
