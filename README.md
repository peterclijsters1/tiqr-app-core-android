# tiqr-app-core-android

SURF.nl library for Android

# Using library in your project

To use SURF library in your project:

1. Add sonatype to repositories
```
   maven {
        setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
   }
```
2. Add core and data dependencies (versions might be different)
```
    implementation("org.tiqr:core:A.B.C")
    implementation("org.tiqr:data:X.Y.Z")
```
3. Setup Coil's ImageLoader
```
    Coil.setImageLoader(ImageLoader.Builder(context = this)
        .crossfade(enable = true)
        .okHttpClient {
            imageOkHttpClient
                .cache(CoilUtils.createDefaultCache(context = this))
                .build()
        }
        .build())
```
4. Override resources, styles, strings and MainActivity (if needed)