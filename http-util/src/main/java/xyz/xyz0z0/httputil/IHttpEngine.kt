package xyz.xyz0z0.httputil

import okhttp3.RequestBody

/**
 * Author: Cheng
 * Date: 2021/8/17 17:42
 * Description: xyz.xyz0z0.httputil
 */
interface IHttpEngine {

    fun get(url: String, params: Map<String, Any>, callBack: EngineCallBack?)

    fun post(url: String, requestBody: RequestBody, callBack: EngineCallBack?)

}