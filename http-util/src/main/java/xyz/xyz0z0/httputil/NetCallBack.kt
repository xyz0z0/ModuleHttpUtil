package xyz.xyz0z0.httputil

import okhttp3.ResponseBody

/**
 * Author: Cheng
 * Date: 2021/8/17 17:44
 * Description: xyz.xyz0z0.httputil
 */
interface NetCallBack {

    fun onError(e: Exception)

    fun onSuccess(responseBody: ResponseBody)

}