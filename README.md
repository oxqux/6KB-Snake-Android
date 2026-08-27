# Snake — dev.oxqux.snake

A tiny Android snake game. This is a heavily compressed fork of the
[Snake](https://play.google.com/store/apps/details?id=com.minik.yilan) app from
Google Play, shrunk down to the smallest possible size.

## Size

| Artifact | Size |
|----------|------|
| `snake-signed.apk` (signed, installs on any Android) | **6287 B** (~6.3 KB) |
| `snake-min.apk` (unsigned, max compression) | **4231 B** (~4.1 KB) |

## How to build an APK of exactly this size

Requirements: Android SDK (build-tools 36.0.0), JDK 17+, `ANDROID_HOME` set.

```bash
# 1. Release build (R8 full mode + shrinkResources are already enabled in build.gradle)
./gradlew clean assembleRelease
APK=app/build/outputs/apk/release/app-release-unsigned.apk

# 2. Drop the build META-INF and recompress the zip as hard as possible
rm -rf /tmp/apk && mkdir /tmp/apk
unzip -o "$APK" -d /tmp/apk >/dev/null
rm -rf /tmp/apk/META-INF
cd /tmp/apk && zip -9 -r /tmp/snake-z9.apk . >/dev/null && cd -

# 3. Align and sign v1 only
#    The v2/v3 signing block adds ~4 KB, so it is disabled to keep the size down.
#    This requires targetSdk 29 in build.gradle: with targetSdk>=30 Android
#    mandates v2 signing and the APK grows to ~12.8 KB.
ZIPALIGN=$ANDROID_HOME/build-tools/36.0.0/zipalign
APKSIGNER=$ANDROID_HOME/build-tools/36.0.0/apksigner
$ZIPALIGN -p 4 /tmp/snake-z9.apk /tmp/snake-aligned.apk
$APKSIGNER sign --v1-signing-enabled true --v2-signing-enabled false --v3-signing-enabled false \
  --ks snake.keystore --ks-key-alias snake --ks-pass pass:android --key-pass pass:android \
  --out snake-signed.apk /tmp/snake-aligned.apk
```

Generate your own key (the APK size does not depend on the key):

```bash
keytool -genkeypair -v -keystore snake.keystore -alias snake \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -storepass android -keypass android -dname "CN=Snake"
```

## How the small size is achieved

- `minifyEnabled` + `shrinkResources` + `android.enableR8.fullMode=true`
- optional manifest attributes stripped (`extractNativeLibs`, `appCategory`,
  `supportsRtl`, `allowBackup`, `versionName`)
- resources compressed, build META-INF metadata removed
- final APK recompressed with `zip -9` and signed v1-only (no v2/v3)

Original: <https://play.google.com/store/apps/details?id=com.minik.yilan>
