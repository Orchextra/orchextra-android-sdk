# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/Android Studio.app/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Obfuscation parameters:
#-dontobfuscate
#-useuniqueclassmembernames
#-keepattributes SourceFile,LineNumberTable
#-allowaccessmodification

# Ignore warnings:
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn com.robotium.**
-dontwarn org.joda.convert.**
-dontwarn okio.**
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
-dontwarn com.squareup.okhttp.internal.huc.**
-dontwarn com.google.appengine.api.urlfetch.**

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

# Keep the pojos used by GSON or Jackson
#-keep class com.futurice.project.models.pojo.** { *; }

# Keep GSON stuff
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.** { *; }

# Keep Jackson stuff
#-keep class org.codehaus.** { *; }
#-keep class com.fasterxml.jackson.annotation.** { *; }

#Mine
-keep class com.gigigo.orchextra.CustomSchemeReceiver { *; }
-keep class com.gigigo.orchextra.Orchextra { *; }
-keep class com.gigigo.orchextra.OrchextraCompletionCallback { *; }
-keep class com.gigigo.orchextra.ORCUser { *; }

# Retrofit
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

 # The official support library.
-keep class android.support.v4.** { *; }
-keepclassmembers class android.support.v4.** {
    *;
 }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keepclassmembers class android.support.v7.** {
    *;
 }
-keep interface android.support.v7.** { *; }



# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$*

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes Exceptions,InnerClasses,Signature

-keep class com.google.api.client.**
-keepclassmembers class com.google.api.client.** {
    *;
 }


-keep class com.google.android.gms.**
-keepclassmembers class com.google.android.gms.** {
    *;
 }
-keep class com.google.gson.**
-keepclassmembers class com.google.gson.** {
    *;
 }

-keep class com.google.api.client.** { *; }
-dontwarn com.google.api.client.*
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.*
