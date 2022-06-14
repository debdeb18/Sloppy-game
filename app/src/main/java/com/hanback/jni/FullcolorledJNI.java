package com.hanback.jni;

public class FullcolorledJNI {
    static {
        System.loadLibrary("sloppy");
    }

    private final int[] led_val = new int[4];
    BackThread thread = new BackThread();

    public void run(int val){
        thread.setDaemon(true);
        thread.setL(val);
        thread.start();
    }

    public void stop(){
        thread.interrupt();
    }

    class BackThread extends Thread{

        int l;

        public void setL(int l) {
            this.l = l;
        }

        public void run() {
            for(int i = 0; i < l*2; i++){
                led_val[0] = 5;
                int res;
                res = 1 + (int)(Math.random() * 154);
                led_val[1] = res;
                res = 1 + (int)(Math.random() * 154);
                led_val[2] = res;
                res = 1 + (int)(Math.random() * 154);
                led_val[3] = res;
                FLEDControl(led_val[0],led_val[1],led_val[2],led_val[3]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            FLEDControl(5,0,0,0);
        }
    }

    public native void FLEDControl(int ledNum, int red, int green, int blue);
}
