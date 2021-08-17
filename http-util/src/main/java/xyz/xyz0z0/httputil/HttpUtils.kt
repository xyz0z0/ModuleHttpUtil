package xyz.xyz0z0.httputil

import android.os.Handler
import android.os.Looper
import android.util.ArrayMap
import okhttp3.FormBody
import okhttp3.RequestBody

/**
 * Author: Cheng
 * Date: 2021/9/27 15:00
 * Description: xyz.xyz0z0.httputil
 */
class HttpUtils {


    private var mParams: MutableMap<String, Any> = ArrayMap<String, Any>()
    private var mUrl: String = ""
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
        mUrl = url
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


    fun execute(callBack: EngineCallBack?) {
//        val callBack: EngineCallBack = engineCallBack ?: EngineCallBack.DEFAULT_CALL_BACK
//        callBack.onPreExecute(mParams)
        if (mType == POST_FORM_TYPE) {
            postCall(mUrl, mParams, callBack)
        }
        if (mType == GET_TYPE) {
            getCall(mUrl, mParams, callBack)
        }
//        if (mType == UPLOAD_FILE_TYPE) {
//            uploadFileCall(mUrl, mParams, callBack)
//        }
//        if (mType == POST_STRING_TYPE) {
//            postStringCall(mUrl, mStringParam, mHeaders, callBack)
//        }
//        if (mType == DOWNLOAD_FILE_TYPE) {
//            downloadFileCall(mUrl, mSaveFilePath, callBack)
//        }
    }

    private fun postCall(url: String, params: Map<String, Any>, callBack: EngineCallBack?) {
        if (mRequestBody == null) {
            mRequestBody = HttpUtils.wrapFormBody(params)
        }
        mRequestBody?.let {
            mHttpEngine.post(url, it, callBack)
        }
    }


    private fun getCall(url: String, params: Map<String, Any>, callBack: EngineCallBack?) {
        mHttpEngine.get(url, params, callBack)
    }


//    private fun postFormCall(url: String, params: Map<String, Any>, callBack: EngineCallBack) {
//        mHttpEngine.postForm(url, params, charset, callBack)
//    }


    fun execute() {
        execute(null)
    }


    companion object {
        const val POST_FORM_TYPE = 0x0011
        const val GET_TYPE = 0x0022
        const val UPLOAD_FILE_TYPE = 0x0033
        const val POST_STRING_TYPE = 0x0044
        const val DOWNLOAD_FILE_TYPE = 0x0055
        var mHttpEngine: IHttpEngine = OkHttpEngine()

        val mMainHandler = Handler(Looper.getMainLooper())

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

    }

}