package xyz.xyz0z0.httputil

import android.util.ArrayMap
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit

/**
 * Author: Cheng
 * Date: 2021/9/27 15:00
 * Description: xyz.xyz0z0.httputil
 */
class HttpUtils(private val httpEngine: IHttpEngine) {


    private var mStrUrl: String = ""
    private var mType = POST_FORM_TYPE


    fun url(url: String): HttpUtils {
        mStrUrl = url
        return this
    }

    fun post(requestBody: RequestBody): String {
        mType = POST_FORM_TYPE
        val host = mStrUrl.toHttpUrl().host
        val responseBody = httpEngine.post(mStrUrl, requestBody)
        mTransformMap[host]?.let {
            return it.transformResponse(responseBody)
        }
        return defaultTransform.transformResponse(responseBody)
    }

    fun get(params: Map<String, Any>): String {
        mType = GET_TYPE
        val host = mStrUrl.toHttpUrl().host
        val pathUrl = params.toParamUrl(mStrUrl)
        val responseBody = httpEngine.get(pathUrl)
        mTransformMap[host]?.let {
            return it.transformResponse(responseBody)
        }
        return defaultTransform.transformResponse(responseBody)
    }


    companion object {

        private val defaultHttpClient = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cookieJar(PersistenceCookieJar())
            .build()

        const val POST_FORM_TYPE = 0x0011
        const val GET_TYPE = 0x0022
        const val UPLOAD_FILE_TYPE = 0x0033
        const val POST_STRING_TYPE = 0x0044
        const val DOWNLOAD_FILE_TYPE = 0x0055
        var mHttpEngine: IHttpEngine = OkHttpEngine(defaultHttpClient)

        val defaultTransform = object : Transform {
            override fun transformResponse(responseBody: ResponseBody): String {
                return responseBody.string()
            }
        }

        fun with(): HttpUtils {
            return HttpUtils(mHttpEngine)
        }


        private val mTransformMap: ArrayMap<String, Transform> = ArrayMap()


        fun registerHost(url: String, netCallBack: Transform) {
            mTransformMap[url.toHttpUrl().host] = netCallBack
        }

        fun initHttpClient(httpClient: OkHttpClient) {
            mHttpEngine = OkHttpEngine(httpClient)
        }

    }

}