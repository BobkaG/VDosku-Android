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

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
# Hilt
  # Сохранение корутин и их классов
-keep class kotlin.coroutines.** { *; }
-keepclassmembers class kotlin.coroutines.Continuation { *; }
# Сохранение классов с аннотациями
-keep class com.example.timetable.** { *; }
-keepclassmembers class * { @javax.inject.Inject <methods>; }
-keepattributes *Annotation*
# Retrofit
-keep class retrofit2.** { *; }
-keepclassmembers class * { @retrofit2.http.* public *; }

# Gson
-keep class com.google.gson.** { *; }

# Room
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * { @androidx.room.* <fields>; @androidx.room.* <methods>; }
# Hilt
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.internal.** { *; }
-keep class * extends dagger.hilt.android.internal.** { *; }
-keep class * extends dagger.hilt.components.** { *; }
-keep class * extends dagger.hilt.processor.internal.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.** { *; }
# Сохранение классов ответа от Retrofit
-keep,allowobfuscation,allowshrinking class retrofit2.Response { *; }
# Отключить обфускацию для классов корутин и их продолжений
-keep,allowobfuscation class kotlin.coroutines.Continuation
# Сохранение классов сериализации
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * { @kotlinx.serialization.Serializable <fields>; @kotlinx.serialization.Serializable <methods>; }
