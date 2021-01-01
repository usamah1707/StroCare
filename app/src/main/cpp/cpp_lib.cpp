//
// Created by Sam on 1/1/2021
//

#include <jni.h>
#include <string>
#include <iostream>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_usamahnashirulhaq_strocare_util_Constants_CallStringMethod(
        JNIEnv *env, jobject thiz) {
    std::string appName = "Strocare v.1.0";
    return env->NewStringUTF(appName.c_str());
}
