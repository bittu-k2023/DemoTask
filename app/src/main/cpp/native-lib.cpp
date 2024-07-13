#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
com_example_liveintutiontask_MainActivity_getEncryptedKey(JNIEnv *env, jobject object) {
    std::string encrypted_key = "This is encrypted key";
    return env->NewStringUTF(encrypted_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_liveintutiontask_ConfigForAPIURL_baseURL(JNIEnv *env, jclass clazz) {
    std::string encrypted_key = "http://43.205.87.112:8080";
    return env->NewStringUTF(encrypted_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_liveintutiontask_ConfigForAPIURL_allTurfs(JNIEnv *env, jclass clazz) {
    std::string encrypted_key = "/vendor/venue/v1/slots";
    return env->NewStringUTF(encrypted_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_liveintutiontask_ConfigForAPIURL_allTurfsDetails(JNIEnv *env, jclass clazz) {
    std::string encrypted_key = "/vendor/venue/v1/turfs";
    return env->NewStringUTF(encrypted_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_liveintutiontask_ConfigForAPIURL_iconBaseUrl(JNIEnv *env, jclass clazz) {
    std::string encrypted_key = "http://43.205.87.112:8080/venture_icons/";
    return env->NewStringUTF(encrypted_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_liveintutiontask_ConfigForAPIURL_bookSlots(JNIEnv *env, jclass clazz) {
    std::string encrypted_key = "/vendor/venue/v1/bookslot";
    return env->NewStringUTF(encrypted_key.c_str());
}