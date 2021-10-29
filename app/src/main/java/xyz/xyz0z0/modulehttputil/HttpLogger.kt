package xyz.xyz0z0.modulehttputil

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Author: Cheng
 * Date: 2021/10/29 11:14
 * Description: xyz.xyz0z0.modulehttputil
 */
class HttpLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        Log.v("HttpLogInfo", message)
    }

}