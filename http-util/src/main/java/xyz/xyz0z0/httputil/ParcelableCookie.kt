package xyz.xyz0z0.httputil

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import okhttp3.Cookie

@Parcelize
class ParcelableCookie(
    val name: String, val value: String, val expiresAt: Long,
    val domain: String, val path: String, val secure: Boolean,
    val httpOnly: Boolean, val hostOnly: Boolean, val persistent: Boolean
) : Parcelable {


    constructor(cookie: Cookie) : this(
        cookie.name,
        cookie.value,
        cookie.expiresAt,
        cookie.domain,
        cookie.path,
        cookie.secure,
        cookie.httpOnly,
        cookie.hostOnly,
        cookie.persistent
    )

    fun getCookie(): Cookie {
        val builder = Cookie.Builder()
            .name(name)
            .value(value)
            .expiresAt(expiresAt)
            .domain(domain)
            .path(path)
        return with(builder) {
            if (secure) {
                builder.secure()
            }
            if (httpOnly) {
                builder.httpOnly()
            }
            if (hostOnly) {
                builder.httpOnly()
            }
            builder.build()
        }
    }
}