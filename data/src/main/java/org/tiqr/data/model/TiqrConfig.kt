package org.tiqr.data.model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle

object TiqrConfig {
    var tokenExchangeBaseUrl: String? = null
        private set

    lateinit var baseUrl: String
        private set

    var protocolVersion: Int = 1
        private set

    var protocolCompatibilityMode: Boolean = true
        private set

    var enforceChallengeHost: String? = null
        private set

    var enrollPathParam: String? = null
        private set

    var authPathParam: String? = null
        private set

    lateinit var enrollScheme: String
        private set

    lateinit var authScheme: String
        private set

    var tokenExchangeEnabled: Boolean = true
        private set

    fun initialize(context: Context) {
        val applicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        readFromBundle(applicationInfo.metaData)
    }

    private fun readFromBundle(bundle: Bundle) {
        val excPrefix =
            "The following metadata parameter should be provided in the AndroidManifest.xml of your app: "
        tokenExchangeBaseUrl = bundle.getString("tiqr_config_token_exchange_base_url")
        baseUrl = bundle.getString("tiqr_config_base_url")
            ?: throw RuntimeException("$excPrefix: 'tiqr_config_base_url'")
        protocolVersion = bundle.getInt("tiqr_config_protocol_version", 1)
        protocolCompatibilityMode =
            bundle.getBoolean("tiqr_config_protocol_compatibility_mode", true)
        enforceChallengeHost = bundle.getString("tiqr_config_enforce_challenge_host")
        enrollPathParam = bundle.getString("tiqr_config_enroll_path_param")
        authPathParam = bundle.getString("tiqr_config_auth_path_param")
        enrollScheme = bundle.getString("tiqr_config_enroll_scheme")
            ?: throw RuntimeException("$excPrefix: 'tiqr_config_enroll_scheme'")
        authScheme = bundle.getString("tiqr_config_auth_scheme")
            ?: throw RuntimeException("$excPrefix: 'tiqr_config_auth_scheme'")
        tokenExchangeEnabled = bundle.getBoolean("tiqr_config_token_exchange_enabled", true)

    }
}