package xyz.xyz0z0.modulehttputil;

import androidx.annotation.NonNull;
import java.io.InputStream;
import xyz.xyz0z0.httputil.EngineCallBack;

/**
 * Author: Cheng
 * Date: 2021/9/27 16:22
 * Description: xyz.xyz0z0.modulehttputil
 */
class TestCallBack implements EngineCallBack {
    @Override public void onError(@NonNull Exception e) {

    }


    @Override public void onSuccess(@NonNull InputStream byteStream) {

    }
}
