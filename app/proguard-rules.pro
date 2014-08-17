# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\development\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
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
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}

-keep class javax.** { *; }

# facebook sdk
-keep class com.facebook.** { *; }
-keepattributes Signature

# Dagger
-dontwarn dagger.internal.codegen.**
-dontwarn com.google.common.**
-dontwarn com.squareup.**
-dontwarn butterknife.internal.**
-keep class **Module
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection

-keepnames !abstract class coffee.*
-keepnames class dagger.Lazy

-keep class dagger.** { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection

#-Keep the names of classes that have fields annotated with @Inject and the fields themselves.
-keepclasseswithmembernames class * {
  @javax.inject.* <fields>;
  @javax.inject.* <init>(...);
  @dagger.* *;
}