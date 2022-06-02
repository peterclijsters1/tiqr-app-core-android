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

5. Add the following parameters to your AndroidManifest.xml:

```xml
<application>
   
     ...
   
    <meta-data android:name="tiqr_config_token_exchange_base_url" android:value="${tiqr_config_token_exchange_base_url}"/>
    <meta-data android:name="tiqr_config_base_url" android:value="${tiqr_config_base_url}"/>
    <meta-data android:name="tiqr_config_protocol_version" android:value="${tiqr_config_protocol_version}"/>
    <meta-data android:name="tiqr_config_protocol_compatibility_mode" android:value="${tiqr_config_protocol_compatibility_mode}"/>
    <meta-data android:name="tiqr_config_enroll_path_param" android:value="${tiqr_config_enroll_path_param}"/>
    <meta-data android:name="tiqr_config_auth_path_param" android:value="${tiqr_config_auth_path_param}"/>
    <meta-data android:name="tiqr_config_enroll_scheme" android:value="${tiqr_config_enroll_scheme}"/>
    <meta-data android:name="tiqr_config_auth_scheme" android:value="${tiqr_config_auth_scheme}"/>
    <meta-data android:name="tiqr_config_token_exchange_enabled" android:value="${tiqr_config_token_exchange_enabled}"/>
</application>
```

And the placeholder fillers to your `build.gradle.kts`, inside your `android.defaultConfig`:
```groovy

android {
   defaultConfig {
      
      ...

      manifestPlaceholders["tiqr_config_token_exchange_base_url"] = "https://tx.tiqr.org/"
      manifestPlaceholders["tiqr_config_base_url"] = "https://demo.tiqr.org"
      manifestPlaceholders["tiqr_config_protocol_version"] = "2"
      manifestPlaceholders["tiqr_config_protocol_compatibility_mode"] =  "true"
      manifestPlaceholders["tiqr_config_enforce_challenge_host"] = "tiqr.nl"
      manifestPlaceholders["tiqr_config_enroll_path_param"] = "tiqrenroll"
      manifestPlaceholders["tiqr_config_auth_path_param"] = "tiqrauth"
      manifestPlaceholders["tiqr_config_enroll_scheme"] = "tiqrenroll"
      manifestPlaceholders["tiqr_config_auth_scheme"] = "tiqrauth"
      manifestPlaceholders["tiqr_config_token_exchange_enabled"] = "true"
   }
}
```
You can adjust the values here.

The following metadata properties are supported:
* `tiqr_config_token_exchange_base_url`: The base URL to the token exchange. This is only required if the token exchange is enabled (see later)
* `tiqr_config_base_url`: The base URL for the API client.
* `tiqr_config_protocol_version`: The main protocol version used by this client.
* `tiqr_config_protocol_compatibility_mode`: If the client should support older protocols than the main protocol version.
* `tiqr_config_enforce_challenge_host`: Optional parameter. When set, the app will check if the HTTP URL has this domain (or a subdomain of it) set as the host.
* `tiqr_config_enroll_path_param`: You can also use HTTPS URLs for enrollment. In that case, this enrollment URL should start with this path parameter
* `tiqr_config_auth_path_param`: You can also use HTTPS URLs for authentication. In that case, this authentication URL should start with this path parameter.
* `tiqr_config_enroll_scheme`: The enrollment URL should start with this HTTP scheme. Do not add the :// to the end of it.
* `tiqr_config_auth_scheme`: The authentication URL should start with this HTTP scheme. Do not add the :// to the end of it.
* `tiqr_config_token_exchange_enabled`: If the token exchange feature is enabled. When set to false, the FCM token will not be converted, but sent directly to the server.

# Making changes in library

1. Make changes in the code

2. Go to https://github.com/SURFnet/tiqr-app-core-android/actions and trigger appropriate workflow. If you are debugging client apps - trigger SNAPSHOT flow,
   if you think that your changes are ready to be released to public - trigger RELEASE flow. If you made changes only in core - trigger CORE flow, if you made changes 
   to data module - you have to trigger both DATA and CORE flows, as core depends on data

# Running the tests

Currently, only the `app` module contains Instrumented Unit Tests. To run them, navigate in Android Studio to the class `app/src/androidTest/TestSuite.kt`, right-click on it, and select `Run 'TestSuite'` . This will run all of the tests in the repository. If you add a new test class, do not forget to add it to the TestSuite by adding it in the SuiteClasses annotation.
