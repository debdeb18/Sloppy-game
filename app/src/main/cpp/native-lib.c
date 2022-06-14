#include <jni.h>
#include <unistd.h>
#include <fcntl.h>
#include <assert.h>
#include <stdio.h>

//can be separated to textlcd.h
#ifndef TEXTLCD_H_
#define TEXTLCD_H_

#define TEXTLCD_ON 		1
#define TEXTLCD_OFF 	2
#define TEXTLCD_INIT 	3
#define TEXTLCD_CLEAR	4

#define TEXTLCD_LINE1	5
#define TEXTLCD_LINE2	6

#endif

static int fd;
static int fdd;

//LED
JNIEXPORT void JNICALL
Java_com_hanback_jni_LedJNI_init(JNIEnv *env, jobject thiz) {
    //fdd = open("/dev/fpga_led", O_WRONLY);
    //assert(fdd != 0);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_LedJNI_on
  (JNIEnv* env, jobject obj, jchar data) {
    fdd = open("/dev/fpga_led", O_WRONLY);
    assert(fdd != 0);

    write(fdd, &data, 1);
    close(fdd);
}

JNIEXPORT void JNICALL
Java_com_hanback_jni_LedJNI_close(JNIEnv *env, jobject thiz) {
    //close(fdd);
}

//textLCD
JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_on
        (JNIEnv * env, jobject obj){
    if (fd == 0)
        fd = open("/dev/fpga_textlcd", O_WRONLY);
    assert(fd != 0);

    ioctl(fd, TEXTLCD_ON);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_off
        (JNIEnv * env, jobject obj){
    if (fd )
    {
        ioctl(fd, TEXTLCD_OFF);
        close(fd);
    }

    fd = 0;
}

JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_initialize
        (JNIEnv * env, jobject obj){
    if (fd == 0)
        fd = open("/dev/fpga_textlcd", O_WRONLY);
    assert(fd != -1);

    ioctl(fd, TEXTLCD_INIT);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_clear
        (JNIEnv * env, jobject obj){
    //if (fd )
    ioctl(fd, TEXTLCD_CLEAR);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_print1Line
        (JNIEnv * env, jobject obj, jstring msg){
    const char *str;

    if (fd )
    {
        str = (*env)->GetStringUTFChars(env, msg, 0);
        ioctl(fd, TEXTLCD_LINE1);
        write(fd, str, strlen(str));
        (*env)->ReleaseStringUTFChars(env, msg, str);
    }

}

JNIEXPORT void JNICALL Java_com_hanback_jni_TextLcdJNI_print2Line
        (JNIEnv * env, jobject obj, jstring msg){
    const char *str;

    if (fd )
    {
        str = (*env)->GetStringUTFChars(env, msg, 0);
        ioctl(fd, TEXTLCD_LINE2);
        write(fd, str, strlen(str));
        (*env)->ReleaseStringUTFChars(env, msg, str);
    }
}

//SEGMENT

JNIEXPORT void JNICALL Java_com_hanback_jni_SegmentJNI_open
        (JNIEnv * env, jobject obj){
    fd = open("/dev/fpga_segment", O_WRONLY);
    assert(fd != -1);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_SegmentJNI_print
        (JNIEnv * env, jobject obj, jint num){
    char buf[7];
    sprintf(buf, "%06d", num);
    printf(stdout, "num: %s\n", num);
    write(fd, buf, 6);
}

JNIEXPORT void JNICALL Java_com_hanback_jni_SegmentJNI_close
        (JNIEnv * env, jobject obj){
    close(fd);
}

//Dot matrix
JNIEXPORT void JNICALL
Java_com_hanback_jni_DotmatrixJNI_DotMatrixControl(JNIEnv *env, jobject thiz, jstring str) {
    const char *pStr;
    int fd, len;

    pStr = (*env)->GetStringUTFChars(env, str, 0);
    len = (*env)->GetStringLength(env, str);

    fd = open("/dev/fpga_dotmatrix", O_RDWR | O_SYNC);

    write(fd, pStr, len);
    close(fd);

    (*env)->ReleaseStringUTFChars(env, str, pStr);
}

//fullcolorled

#define FULL_LED1	9
#define	FULL_LED2	8
#define FULL_LED3	7
#define FULL_LED4	6
#define ALL_LED		5

void
Java_com_hanback_jni_FullcolorledJNI_FLEDControl(JNIEnv* env, jobject thiz, jint led_num, jint val1, jint val2, jint val3)
{
    int fd,ret;
    char buf[3];

    fd = open("/dev/fpga_fullcolorled", O_WRONLY);
    if (fd < 0)
    {
        exit(-1);
    }
    ret = (int)led_num;
    switch(ret)
    {
        case FULL_LED1:
            ioctl(fd,FULL_LED1);
            break;
        case FULL_LED2:
            ioctl(fd,FULL_LED2);
            break;
        case FULL_LED3:
            ioctl(fd,FULL_LED3);
            break;
        case FULL_LED4:
            ioctl(fd,FULL_LED4);
            break;
        case ALL_LED:
            ioctl(fd,ALL_LED);
            break;
    }
    buf[0] = val1;
    buf[1] = val2;
    buf[2] = val3;

    write(fd,buf,3);

    close(fd);
}