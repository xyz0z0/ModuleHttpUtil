package xyz.xyz0z0.httputil

import okhttp3.RequestBody
import okhttp3.ResponseBody

/**
 * Author: Cheng
 * Date: 2021/8/17 17:42
 * Description: xyz.xyz0z0.httputil
 */
interface IHttpEngine {

    fun get(pathUrl: String, callBack: NetCallBack)

    suspend fun getSuspend(pathUrl: String): ResponseBody

    fun post(url: String, requestBody: RequestBody, callBack: NetCallBack)

    suspend fun postSuspend(url: String, requestBody: RequestBody): ResponseBody

}