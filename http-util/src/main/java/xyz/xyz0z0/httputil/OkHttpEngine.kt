package xyz.xyz0z0.httputil

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

/**
 * Author: Cheng
 * Date: 2021/9/27 14:55
 * Description: xyz.xyz0z0.httputil
 */
class OkHttpEngine : IHttpEngine {

    companion object {

        val mOkHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cookieJar(PersistenceCookieJar())
            .build()

    }


    override fun get(pathUrl: String): ResponseBody {
        val request = Request.Builder().url(pathUrl).build()
        val response = mOkHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return it
            }
        }
        throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")

    }

    private var cookieStrList: List<String> = mutableListOf()


    @Suppress("BlockingMethodInNonBlockingContext")
    override fun post(url: String, requestBody: RequestBody): ResponseBody {
        val requestBuilder = Request.Builder().url(url).post(requestBody)
        val response = mOkHttpClient.newCall(requestBuilder.build()).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return it
            }
        }
        throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")

    }
}