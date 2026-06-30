# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the default ProGuard configuration included with the Android SDK.

# Keep Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
