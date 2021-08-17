package xyz.xyz0z0.httputil

import java.io.InputStream

/**
 * Author: Cheng
 * Date: 2021/8/17 17:44
 * Description: xyz.xyz0z0.httputil
 */
interface EngineCallBack {

    fun onError(e: Exception)

    fun onSuccess(byteStream: InputStream)

}