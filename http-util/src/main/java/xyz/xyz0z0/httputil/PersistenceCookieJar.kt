package xyz.xyz0z0.httputil

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author chengxugang 2020/6/14.
 */
class PersistenceCookieJar : CookieJar {

    private val cache: MutableList<Cookie> = mutableListOf()
    private val persistence: CookiePersistor = RamCookiePersistent()


    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
        persistence.saveAll(filterPersistentCookies(cookies))
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookiesToRemove: MutableList<Cookie> = mutableListOf()
        val validCookies: MutableList<Cookie> = mutableListOf()

        for (cookie in cache) {
            if (isCookieExpired(cookie)) {
                cookiesToRemove.add(cookie)
            } else if (cookie.matches(url)) {
                validCookies.add(cookie)
            }
        }
        persistence.removeAll(cookiesToRemove)
        cache.removeAll(cookiesToRemove)
        return validCookies
    }

    private fun filterPersistentCookies(cookies: List<Cookie>): List<Cookie> {
        val persistentCookies: MutableList<Cookie> = mutableListOf()
        for (cookie in cookies) {
            if (cookie.persistent) {
                persistentCookies.add(cookie)
            }
        }
        return persistentCookies
    }

    private fun isCookieExpired(cookie: Cookie): Boolean {
        return cookie.expiresAt < System.currentTimeMillis()
    }

}