package xyz.xyz0z0.httputil

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * Author: Cheng
 * Date: 2021/9/27 14:55
 * Description: xyz.xyz0z0.httputil
 */
class OkHttpEngine(private val httpClient: OkHttpClient) : IHttpEngine {


    override fun get(pathUrl: String): ResponseBody {
        val request = Request.Builder().url(pathUrl).build()
        val response = httpClient.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return it
            }
        }
        throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")
    }

    private var cookieStrList: List<String> = mutableListOf()


    override fun post(url: String, requestBody: RequestBody): ResponseBody {
        val requestBuilder = Request.Builder().url(url).post(requestBody)
        val response = httpClient.newCall(requestBuilder.build()).execute()
        if (response.isSuccessful) {
            response.body?.let {
                return it
            }
        }
        throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")

    }
}