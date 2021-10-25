package xyz.xyz0z0.httputil

import android.os.Handler
import android.os.Looper
import android.util.ArrayMap
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * Author: Cheng
 * Date: 2021/9/27 15:00
 * Description: xyz.xyz0z0.httputil
 */
class HttpUtils {


    private var mParams: MutableMap<String, Any> = ArrayMap<String, Any>()
    private var mStrUrl: String = ""
    private var mType = POST_FORM_TYPE
    private var mRequestBody: RequestBody? = null


    fun addParam(key: String, value: Any): HttpUtils {
        mParams[key] = value
        return this
    }

    fun addParams(params: Map<String, Any>): HttpUtils {
        mParams.putAll(params)
        return this
    }

    fun url(url: String): HttpUtils {
        mStrUrl = url
        return this
    }

    @JvmOverloads
    fun post(requestBody: RequestBody? = null): HttpUtils {
        requestBody?.let {
            mRequestBody = it
        }
        mType = POST_FORM_TYPE
        return this
    }

    fun get(): HttpUtils {
        mType = GET_TYPE
        return this
    }

    fun call(): String {
        val host = mStrUrl.toHttpUrl().host
        if (mType == POST_FORM_TYPE) {
            val responseBody = mHttpEngine.post(mStrUrl, mRequestBody!!)
            transformMap[host]?.let {
                return it.transformResponse(responseBody)
            }
            return defaultTransform.transformResponse(responseBody)
        }
        if (mType == GET_TYPE) {
            val pathUrl = joinParams(mStrUrl, mParams)
            val responseBody = mHttpEngine.get(pathUrl)
            transformMap[host]?.let {
                return it.transformResponse(responseBody)
            }
            return defaultTransform.transformResponse(responseBody)
        }
        throw HttpUtilException(0, "Code Error")
    }

    companion object {
        const val POST_FORM_TYPE = 0x0011
        const val GET_TYPE = 0x0022
        const val UPLOAD_FILE_TYPE = 0x0033
        const val POST_STRING_TYPE = 0x0044
        const val DOWNLOAD_FILE_TYPE = 0x0055
        var mHttpEngine: IHttpEngine = OkHttpEngine()

        val mMainHandler = Handler(Looper.getMainLooper())

        val defaultTransform = object : Transform {
            override fun transformResponse(responseBody: ResponseBody): String {
                return responseBody.string()
            }

        }

        fun with(): HttpUtils {
            return HttpUtils()
        }

        /**
         * 组转 FromBody 参数
         */
        fun wrapFormBody(params: Map<String, Any>): FormBody {
            val fromBodyBuilder = FormBody.Builder()
            for ((key, value) in params) {
                fromBodyBuilder.add(key, value.toString())
            }
            return fromBodyBuilder.build()
        }

        /**
         * 用于 get 请求拼接参数
         */
        fun joinParams(url: String, params: Map<String, Any>): String {
            if (params.isEmpty()) {
                return url
            }
            val sb = StringBuilder(url)
            if (!url.contains("?")) {
                sb.append("?")
            } else {
                if (!url.endsWith("?")) {
                    sb.append("&")
                }
            }
            for ((key, value) in params) {
                sb.append("$key=$value&")
            }
            sb.deleteCharAt(sb.length - 1)
            return sb.toString()
        }

        val transformMap: ArrayMap<String, Transform> = ArrayMap()


        fun registerHost(url: String, netCallBack: Transform) {
            transformMap[url.toHttpUrl().host] = netCallBack
        }

    }

}