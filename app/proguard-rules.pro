# General R8/ProGuard rules
-keepattributes Signature, *Annotation*, EnclosingMethod, InnerClasses
-keepattributes SourceFile, LineNumberTable

# Kotlinx Serialization
# Needed to keep generated serializers for @Serializable classes (like your Screen routes)
-keep @interface kotlinx.serialization.Serializable
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static ** Companion;
    *** $serializer;
    *** serializer(...);
}
# Keep 'object' declarations marked @Serializable (CalculatorScreen, MoreScreen, etc.)
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Navigation 3
# Keep all classes implementing NavKey to ensure route resolution works
-keep class * implements androidx.navigation3.runtime.NavKey { *; }

# Google Play Core (App Update and Review)
# Necessary for reflection-based lookups in the Play Store service
-keep class com.google.android.play.core.common.IntentSenderForResultStarter { *; }
-keep class com.google.android.play.core.tasks.** { *; }
-keep class com.google.android.play.core.appupdate.** { *; }
-keep class com.google.android.play.core.review.** { *; }
-keep class com.google.android.play.core.install.model.** { *; }

# BUG FIX: Required for review-ktx 2.0.2+ to prevent R8 build errors
-dontwarn com.google.android.gms.common.annotation.NoNullnessRewrite