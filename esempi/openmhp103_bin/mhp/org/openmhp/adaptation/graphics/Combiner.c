#include "jni.h"
#include "org_openmhp_adaptation_graphics_Combiner.h"

JNIEXPORT jintArray JNICALL Java_org_openmhp_adaptation_graphics_Combiner_combine
  (JNIEnv *env, jobject, jintArray first, jintArray second)
{
  jsize len1 = (*env)->GetArrayLength(env, first);
  jint *body1 = (*env)->GetIntArrayElements(env, first, 0);

  jsize len2 = (*env)->GetArrayLength(env, second);
  jint *body2 = (*env)->GetIntArrayElements(env, second, 0);

  int i = 0;

  for (i=0; i<len1; i++) {
    body1[i]+=body2[i];
  }
  (*env)->ReleaseIntArrayElements(env, first, body1, 0);
  (*env)->ReleaseIntArrayElements(env, second, body2, 0);

  return body1;

}