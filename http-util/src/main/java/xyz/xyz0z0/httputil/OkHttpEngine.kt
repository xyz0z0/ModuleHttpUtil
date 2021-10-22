package xyz.xyz0z0.httputil

import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
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

    private fun callOnFailure(callBack: NetCallBack, e: Exception) {
        HttpUtils.mMainHandler.post {
            callBack.onError(e)
        }
    }


    override fun get(pathUrl: String, callBack: NetCallBack) {
        val request = Request.Builder().url(pathUrl).build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callBack.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    LogUtils.json("cxg-get", response.headers.toMultimap())
                    response.body?.let {
                        callBack.onSuccess(it)
                        return
                    }
                }
                callOnFailure(callBack, HttpUtilException(response.code, response.message))

            }
        })
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getSuspend(pathUrl: String): ResponseBody {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder().url(pathUrl).build()
            val response = mOkHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.let {
                    return@withContext it
                }
            }
            throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")
        }
    }

    private var cookieStrList: List<String> = mutableListOf()


    override fun post(url: String, requestBody: RequestBody, callBack: NetCallBack) {
        val requestBuilder = Request.Builder().url(url).post(requestBody)
        mOkHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callOnFailure(callBack, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    //
                    val cookies = response.headers.values("Set-Cookie")
                    for (cookie in cookies) {
                        val cookie = Cookie.parse(response.request.url, cookie)
                        LogUtils.json("cxg", cookie)
                    }
                    response.body?.let {
                        callBack.onSuccess(it)
                        return
                    }
                    HttpUtils.mMainHandler.post {
                        callBack.onError(HttpUtilException(-1, "Null Body"))
                    }
                } else {
                    HttpUtils.mMainHandler.post {
                        callBack.onError(HttpUtilException(response.code, response.message))
                    }
                }
            }
        })
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun postSuspend(url: String, requestBody: RequestBody): ResponseBody {
        return withContext(Dispatchers.IO) {
            val requestBuilder = Request.Builder().url(url).post(requestBody)
            val response = mOkHttpClient.newCall(requestBuilder.build()).execute()
            if (response.isSuccessful) {
                response.body?.let {
                    return@withContext it
                }
            }
            throw HttpUtilException(response.code, "Msg:${response.message},Body:${response.body?.string()}")
        }
    }
}