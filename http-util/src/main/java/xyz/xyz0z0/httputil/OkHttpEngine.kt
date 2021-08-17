package xyz.xyz0z0.httputil

import com.blankj.utilcode.util.LogUtils
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

    private fun callOnFailure(callBack: EngineCallBack?, e: Exception) {
        HttpUtils.mMainHandler.post {
            callBack?.onError(e)
        }
    }


    override fun get(url: String, params: Map<String, Any>, callBack: EngineCallBack?) {
        val pathUrl = HttpUtils.joinParams(url, params)
        val request = Request.Builder().url(pathUrl).build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callOnFailure(callBack, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    LogUtils.json("cxg-get", response.headers.toMultimap())
                    response.body?.let {
                        callBack?.onSuccess(it.byteStream())
                        return
                    }
                    callOnFailure(callBack, HttpUtilException(-1, "Null Body"))
                } else {
                    callOnFailure(callBack, HttpUtilException(response.code, response.message))
                }
            }
        })
    }

    private var cookieStrList: List<String> = mutableListOf()

    override fun post(url: String, requestBody: RequestBody, callBack: EngineCallBack?) {
        val requestBuilder = Request.Builder().url(url).post(requestBody)
        mOkHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callOnFailure(callBack, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    //////
                    val cookies = response.headers.values("Set-Cookie")
                    for (cookie in cookies) {
                        val cookiee = Cookie.parse(response.request.url, cookie)
                        LogUtils.json("cxg", cookiee)
                    }
                    response.body?.let {
                        callBack?.onSuccess(it.byteStream())
                        return
                    }
                    HttpUtils.mMainHandler.post {
                        callBack?.onError(HttpUtilException(-1, "Null Body"))
                    }
                } else {
                    HttpUtils.mMainHandler.post {
                        callBack?.onError(HttpUtilException(response.code, response.message))
                    }
                }
            }
        })
    }
}