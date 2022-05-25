package org.tiqr.data.model

import android.net.Uri
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.tiqr.data.BuildConfig
import timber.log.Timber

class AuthenticationUrlParams private constructor(
    val username: String,
    val protocolVersion: Int,
    val sessionKey: String,
    val challenge: String,
    val serverIdentifier: String,
    val returnUrl: String?
) {
    companion object {
        /**
         * Tries to parse the challenge authentication URL params from a given URL.
         * If the parse could not complete because of missing information, null will be returned.
         */
        fun parseFromUrl(url: String): AuthenticationUrlParams? {
            return if (url.startsWith("http")) {
                // New format URL
                parseNewFormatUrl(url)
            } else if (url.startsWith(BuildConfig.TIQR_AUTH_SCHEME)) {
                // Old format URL
                parseOldFormatUrl(url)
            } else {
                Timber.i("Could not parse URL for authentication challenge, URL is: '$url'")
                null
            }
        }

        private fun parseOldFormatUrl(url: String): AuthenticationUrlParams? {
            val httpUrl =
                url.replaceFirst(BuildConfig.TIQR_AUTH_SCHEME, "http://").toHttpUrlOrNull()
            if (httpUrl == null) {
                Timber.w("Could not parse old format URL.")
                return null
            }
            val username = httpUrl.username
            val protocolVersion = httpUrl.pathSegments.getOrNull(3)?.toInt() ?: 0
            val sessionKey = httpUrl.pathSegments[0]
            val challenge = httpUrl.pathSegments[1]
            val returnUrl = httpUrl.query?.toHttpUrlOrNull()?.toString()
            val serverIdentifier = httpUrl.host
            return AuthenticationUrlParams(
                username = username,
                protocolVersion = protocolVersion,
                sessionKey = sessionKey,
                challenge = challenge,
                returnUrl = returnUrl,
                serverIdentifier = serverIdentifier
            )
        }

        private fun parseNewFormatUrl(url: String): AuthenticationUrlParams? {
            val uri = try {
                Uri.parse(url)
            } catch (ex: Exception) {
                Timber.w(ex, "Unable to parse URL: '$url'")
                return null
            }
            if (BuildConfig.ENFORCE_CHALLENGE_HOST.isNotBlank()) {
                val host = uri.host?.lowercase()
                if (host == null ||
                    (host != BuildConfig.ENFORCE_CHALLENGE_HOST && host.endsWith("." + BuildConfig.ENFORCE_CHALLENGE_HOST))
                ) {
                    Timber.w("Host was expected to be a subdomain of: ${BuildConfig.ENFORCE_CHALLENGE_HOST}, but it was actually: $host.");
                    return null
                }
            }
            val firstPathParam = uri.pathSegments.firstOrNull()
            if (firstPathParam == null) {
                Timber.w("Expected a path parameter, got nothing, this is not a valid challenge URL.")
                return null
            }
            if (firstPathParam.lowercase() != BuildConfig.TIQR_AUTH_PATH_PARAM.lowercase()) {
                Timber.w("Challenge URL not according to format. Expected path parameter: '${BuildConfig.TIQR_AUTH_PATH_PARAM}', got: '$firstPathParam'.")
                return null
            }
            val username = uri.getQueryParameter("u") ?: return null
            val protocolVersion = uri.getQueryParameter("v")?.toIntOrNull() ?: 0
            val challenge = uri.getQueryParameter("c") ?: return null
            val sessionKey = uri.getQueryParameter("s") ?: return null
            val serverIdentifier = uri.getQueryParameter("i") ?: return null
            val returnUrl: String? = null

            return AuthenticationUrlParams(
                username = username,
                protocolVersion = protocolVersion,
                sessionKey = sessionKey,
                challenge = challenge,
                returnUrl = returnUrl,
                serverIdentifier = serverIdentifier
            )
        }
    }
}
