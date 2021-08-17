package xyz.xyz0z0.modulehttputil

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import xyz.xyz0z0.httputil.EngineCallBack
import xyz.xyz0z0.httputil.HttpUtils
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnGetBanner: Button
    private lateinit var btnGetUnread: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()


        btnLogin.setOnClickListener {
            HttpUtils.with()
                    .url(Constants.loginUrl)
                    .addParam("username", "xyz0z0")
                    .addParam("password", "__xyz0z0")
                    .post()
                    .execute(object : EngineCallBack {
                        override fun onError(e: Exception) {
                            e.printStackTrace()
                        }


                        override fun onSuccess(byteStream: InputStream) {
                            val str = ConvertUtils.inputStream2String(byteStream, "UTF-8")
                            LogUtils.json("cxg", str)
                        }
                    })
        }

        btnGetBanner.setOnClickListener {
            HttpUtils.with()
                    .url(Constants.bannerUrl)
                    .get()
                    .execute(object : EngineCallBack {

                        override fun onError(e: Exception) {
                            e.printStackTrace()
                        }

                        override fun onSuccess(byteStream: InputStream) {
                            val str = ConvertUtils.inputStream2String(byteStream, "UTF-8")
                            LogUtils.json("cxg", str)
                        }
                    })
        }

        btnGetUnread.setOnClickListener {
            HttpUtils.with()
                    .url(Constants.unreadCountUrl)
                    .get()
                    .execute(object : EngineCallBack {

                        override fun onError(e: Exception) {
                            e.printStackTrace()
                        }

                        override fun onSuccess(byteStream: InputStream) {
                            val str = ConvertUtils.inputStream2String(byteStream, "UTF-8")
                            LogUtils.json("cxg", str)
                        }
                    })
        }

    }

    private fun findView() {
        btnLogin = findViewById(R.id.btnLogin)
        btnGetBanner = findViewById(R.id.btnGetBanner)
        btnGetUnread = findViewById(R.id.btnGetUnread)
    }


}