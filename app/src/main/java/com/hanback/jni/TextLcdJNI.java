package com.hanback.jni;

public class TextLcdJNI {
    static {
        System.loadLibrary("sloppy");
    }

    public void printTextLcd(String str1, String str2){
        clear();
        print1Line(str1);
        print2Line(str2);
    }

    public native void on();
    public native void off();
    public native void initialize();
    public native void clear();

    public native void print1Line(String str);
    public native void print2Line(String str);
}
