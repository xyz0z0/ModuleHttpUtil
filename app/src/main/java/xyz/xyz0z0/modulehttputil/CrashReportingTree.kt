package xyz.xyz0z0.modulehttputil

import android.util.Log
import com.blankj.utilcode.util.LogUtils
import timber.log.Timber

/**
 * Author: Cheng
 * Date: 2021/10/29 16:39
 * Description: xyz.xyz0z0.modulehttputil
 */
class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }
        LogUtils.json("xxx",message)
    }
}