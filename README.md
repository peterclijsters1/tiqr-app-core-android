# tiqr-app-core-android

SURF.nl library for Android

# Using library in your project

To use SURF library in your project:

1. Add sonatype to repositories
```
   maven {
        setUrl("https://s01.oss.sonatype.org/content/repositories/releases/")
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

# Making changes in library

1. Make changes in the code

2. Go to https://github.com/SURFnet/tiqr-app-core-android/actions and trigger appropriate workflow. If you are debugging client apps - trigger SNAPSHOT flow,
   if you think that your changes are ready to be released to public - trigger RELEASE flow. If you made changes only in core - trigger CORE flow, if you made changes 
   to data module - you have to trigger both DATA and CORE flows, as core depends on data

# Running the tests

Currently, only the `app` module contains Instrumented Unit Tests. To run them, navigate in Android Studio to the class `app/src/androidTest/TestSuite.kt`, right-click on it, and select `Run 'TestSuite'` . This will run all of the tests in the repository. If you add a new test class, do not forget to add it to the TestSuite by adding it in the SuiteClasses annotation.
