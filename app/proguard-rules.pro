# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Support for Android Advertiser ID.
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}

# Support for Google Play Services
# http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# For a bug in AudioFocusHandler where we have a workaround via reflection
-keep class androidx.media2.player.AudioFocusHandler {*;}
-keepnames class androidx.media2.player.MediaPlayer {*;}

    # Add this global rule
    -keepattributes Signature

    # This rule will properly ProGuard all the model classes in
    # the package com.yourcompany.models.
    # Modify this rule to fit the structure of your app.
    -keepclassmembers class com.od.msbte_exam_pre.** {
      *;
    }


    # Keep custom model classes
    -keep class com.google.firebase.example.fireeats.java.model.** { *; }
    -keep class com.google.firebase.example.fireeats.kotlin.model.** { *; }

    # https://github.com/firebase/FirebaseUI-Android/issues/1175
    -dontwarn okio.**
    -dontwarn retrofit2.Call
    -dontnote retrofit2.Platform$IOS$MainThreadExecutor
    -keep class android.support.v7.widget.RecyclerView { *; }

    # Firebase Notification, Crashlytics, Analytics
    -keep class io.invertase.firebase.** { *; }
    -keep class io.invertase.firebase.messaging.** { *; }
    -keep class android.app.MiuiNotification { *; }
    -keep public class com.google.android.gms.ads.** {
        public *;
    }

    -keep public class com.google.ads.** {
        public *;
    }
    -keep class com.google.ads.** # Don't proguard AdMob classes
    -dontwarn com.google.ads.** # Temporary workaround for v6.2.1. It gives a warning that you can ignore

    -keep public class com.google.android.gms.ads.**{
       public *;
    }

    # For old ads classes
    -keep public class com.google.ads.**{
       public *;
    }

    # For mediation
    -keepattributes *Annotation*

    # Other required classes for Google Play Services
    # Read more at http://developer.android.com/google/play-services/setup.html
    -keep class * extends java.util.ListResourceBundle {
       protected Object[][] getContents();
    }

    -keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
       public static final *** NULL;
    }

    -keepnames @com.google.android.gms.common.annotation.KeepName class *
    -keepclassmembernames class * {
       @com.google.android.gms.common.annotation.KeepName *;
    }

    -keepnames class * implements android.os.Parcelable {
       public static final ** CREATOR;
    }
    -keep public class com.google.firebase.analytics.FirebaseAnalytics {
        public *;
    }

    -keep public class com.google.android.gms.measurement.AppMeasurement {
        public *;
    }

     -keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,
        LineNumberTable, *Annotation*, EnclosingMethod
        -dontwarn android.webkit.JavascriptInterface
        -dontwarn com.startapp.**

        -dontwarn org.jetbrains.annotations.**

# Google
-dontwarn com.google.android.gms.common.GoogleApiAvailabilityLight
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient$Info

# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**

# GSON
-keepattributes *Annotation*
-keepattributes Signature
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# OkHttp + Okio
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# Facbook Ads
-keep class com.facebook.** { *; }
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**
#Startapp
-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,
LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**

# Vungle # Vungle
-dontwarn com.vungle.warren.downloader.DownloadRequestMediator$Status
-dontwarn com.vungle.warren.error.VungleError$ErrorCode
# Google
-dontwarn com.google.android.gms.common.GoogleApiAvailabilityLight
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient
-dontwarn com.google.android.gms.ads.identifier.AdvertisingIdClient$Info
# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# GSON
-keepattributes *Annotation*
-keepattributes Signature
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
# OkHttp + Okio
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# ironsource
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# For communication with AdColony's WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# Keep ADCNative class members unobfuscated
-keepclassmembers class com.adcolony.sdk.ADCNative** {
    *;
 }
 -dontwarn com.facebook.ads.internal.**
 -keeppackagenames com.facebook.*
 -keep public class com.facebook.ads.** {*;}
 -keep public class com.facebook.ads.**
 { public protected *; }

 # Keep filenames and line numbers for stack traces
 -keepattributes SourceFile,LineNumberTable
 # Keep JavascriptInterface for WebView bridge
 -keepattributes JavascriptInterface
 # Sometimes keepattributes is not enough to keep annotations
 -keep class android.webkit.JavascriptInterface {
    *;
 }
 # Keep all classes in Unity Ads package
 -keep class com.unity3d.ads.** {
    *;
 }
 # Keep all classes in Unity Services package
 -keep class com.unity3d.services.** {
    *;
 }
 -dontwarn com.google.ar.core.**
 -dontwarn com.unity3d.services.**
 -dontwarn com.ironsource.adapters.unityads.**

 # Vungle
 -keep class com.vungle.warren.** { *; }
 -dontwarn com.vungle.warren.error.VungleError$ErrorCode
 # Moat SDK
 -keep class com.moat.** { *; }
 -dontwarn com.moat.**
 # Okio
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 # Retrofit
 -dontwarn okio.**
 -dontwarn retrofit2.Platform$Java8
 # Gson
 -keepattributes Signature
 -keepattributes *Annotation*
 -dontwarn sun.misc.**
 -keep class com.google.gson.examples.android.model.** { *; }
 -keep class * implements com.google.gson.TypeAdapterFactory
 -keep class * implements com.google.gson.JsonSerializer
 -keep class * implements com.google.gson.JsonDeserializer
 # Google Android Advertising ID
 -keep class com.google.android.gms.internal.** { *; }
 -dontwarn com.google.android.gms.ads.identifier.**

 -keep class com.startapp.** {
       *;
 }

 -keep class com.truenet.** {
       *;
 }

 -keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,
 LineNumberTable, *Annotation*, EnclosingMethod
 -dontwarn android.webkit.JavascriptInterface
 -dontwarn com.startapp.**

 -dontwarn org.jetbrains.annotations.**

