package xyz.xyz0z0.httputil

import okhttp3.Cookie

/**
 * Author: Cheng
 * Date: 2021/10/11 16:46
 * Description: xyz.xyz0z0.httputil
 */
interface CookiePersistor {

    fun loadAll(): List<Cookie>

    fun saveAll(cookies: Collection<Cookie>)

    fun removeAll(cookies: Collection<Cookie>)

    fun clear()

}
