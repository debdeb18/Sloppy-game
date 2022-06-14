package com.hanback.jni;

public class SegmentJNI {
    static {
        System.loadLibrary("sloppy");
    }

    public native void open();
    public native void print(int num);
    public native void close();
}
