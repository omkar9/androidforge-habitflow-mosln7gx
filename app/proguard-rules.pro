# Add project specific ProGuard rules here.
# You can control the default rules by using the -dontobfuscate
# option in your build configuration.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Room Persistence Library
-keep class androidx.room.RoomDatabase { *
; }
-keep class androidx.room.TypeConverter { *
; }
-keep class * extends androidx.room.RoomDatabase { *
; }
-keep class * extends androidx.room.RxRoom { *
; }
-keep public interface * extends androidx.room.migration.Migration { *
; }
-keep class * implements androidx.room.migration.Migration { *
; }
-keep class * extends androidx.room.TypeConverters { *
; }
-keep class * implements androidx.room.InvalidationTracker.Observer { *
; }
-keep class * implements androidx.room.RoomWarnings { *
; }
-keep class * implements androidx.room.EntityDeletionOrUpdateAdapter { *
; }
-keep class * implements androidx.room.EntityInsertionAdapter { *
; }
-keep class * implements androidx.room.SharedSQLiteStatement { *
; }
-keep class * implements androidx.room.util.TableInfo.Column { *
; }
-keep class * implements androidx.room.util.TableInfo.ForeignKey { *
; }

# Hilt
-keep class dagger.hilt.android.internal.managers.ActivityComponentManager
-keep class dagger.hilt.android.internal.managers.FragmentComponentManager
-keep class dagger.hilt.android.internal.managers.ServiceComponentManager
-keep class dagger.hilt.android.internal.managers.ViewComponentManager
-keep class dagger.hilt.android.internal.managers.BroadcastReceiverComponentManager
-keep class dagger.hilt.android.internal.managers.ContentProviderComponentManager

# General Dagger/Hilt rules
-keepnames @dagger.hilt.android.HiltAndroidApp class * { }
-keepnames @dagger.hilt.android.AndroidEntryPoint class * { }
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * { }
-keepnames @dagger.Module class * { }
-keepnames @dagger.Provides class * { }
-keepnames @dagger.Binds class * { }

# For AdMob
-keep public class com.google.android.gms.ads.** { *; }
-keep public class com.google.ads.** { *; }
-keep public class com.google.android.gms.common.internal.safeparcel.** { *; }
-keep public class com.google.android.gms.common.GooglePlayServicesUtil { *; }
-keep public class com.google.android.gms.dynamite.** { *; }
-keep public class com.google.android.gms.internal.ads.** { *; }
-keep public class com.google.android.gms.ads.AdActivity { *; }
-keep public class com.google.android.gms.ads.InterstitialAd { *; }
-keep public class com.google.android.gms.ads.reward.RewardItem { *; }
-keep public class com.google.android.gms.ads.reward.RewardedVideoAdListener { *; }
-keep public class com.google.android.gms.ads.MobileAds { *; }
-keep public class com.google.android.gms.ads.AdRequest { *; }
-keep public class com.google.android.gms.ads.AdView { *; }
-keep public class com.google.android.gms.ads.doubleclick.PublisherAdRequest { *; }
-keep public class com.google.android.gms.ads.doubleclick.PublisherAdView { *; }

# Timber
-dontwarn timber.log.**
-keep class timber.log.** { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepnames class kotlinx.coroutines.flow.SharedFlow { *; }
-keepnames class kotlinx.coroutines.flow.StateFlow { *; }

# For Java 8 Time API (LocalDate, etc.)
-dontwarn java.time.**
-dontwarn org.threeten.bp.**
-keep class org.threeten.bp.** { *; }
-keep class java.time.** { *; }