package com.hanback.jni;

public class LedJNI {
    // Used to load the 'sloppy' library on application startup.
    static {
        System.loadLibrary("sloppy");
    }

    public native void on(char data);
    public native void close();
    public native void init();
}
