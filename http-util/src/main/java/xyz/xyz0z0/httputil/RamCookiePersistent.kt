package xyz.xyz0z0.httputil

import okhttp3.Cookie

/**
 * Author: Cheng
 * Date: 2021/10/11 16:57
 * Description: xyz.xyz0z0.httputil
 */
class RamCookiePersistent : CookiePersistor {

    private var cookie: MutableList<Cookie> = mutableListOf()

    override fun loadAll(): List<Cookie> {
        return cookie
    }

    override fun saveAll(cookies: Collection<Cookie>) {
        cookie.clear()
        cookie.addAll(cookies)
    }

    override fun removeAll(cookies: Collection<Cookie>) {
        cookie.removeAll(cookies)
    }

    override fun clear() {
        cookie.clear()
    }
}