package xyz.xyz0z0.modulehttputil

import android.os.Bundle
import android.util.ArrayMap
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import xyz.xyz0z0.httputil.HttpUtils
import xyz.xyz0z0.httputil.PersistenceCookieJar
import xyz.xyz0z0.httputil.Transform
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

val REGEX_CUSTOMER = Regex("\\{.*\\}")


val start = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><getLoginInfo_newResponse xmlns=\"http://tempuri.org/\"><getLoginInfo_newResult>"
val end = "</getLoginInfo_newResult></getLoginInfo_newResponse></soap:Body></soap:Envelope>"

fun main() {
    val result = "<<<xdxdxd>>>{gds{xx}fgds}gdsgfds"

    println(REGEX_CUSTOMER.find(result)?.value)
}

class MainActivity : AppCompatActivity() {

    private lateinit var btnCustomerLogin: Button
    private lateinit var btnLogin: Button
    private lateinit var btnGetBanner: Button
    private lateinit var btnGetUnread: Button


    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())
//        Timber.plant(CrashReportingTree())

        findView()

        val netCallBack1: Transform = object : Transform {


            override fun transformResponse(responseBody: ResponseBody): String {
                return REGEX_CUSTOMER.find(responseBody.string())?.value ?: ""
            }
        }

        val jhUrl = "http://9.0.2.139:9032/GYWebService.asmx"
        HttpUtils.registerHost(jhUrl, netCallBack1)

//        val logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(HttpLogger())
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val defaultHttpClient = OkHttpClient.Builder()
//            .callTimeout(30, TimeUnit.SECONDS)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(logInterceptor)
//            .cookieJar(PersistenceCookieJar())
//            .build()
//        HttpUtils.initHttpClient(defaultHttpClient)


        btnCustomerLogin.setOnClickListener {

//            val map = ArrayMap<String, String>()
//            map["userCode"] = "0507"
//            map["password"] = "gybx1234"
//            val method = "getLoginInfo_new"

//            GlobalScope.launch {
//                val result = HttpUtils.with()
//                    .url(jhUrl)
//                    .post(method.toWebServiceBody(map))
//                    .call().fromJson(UserInfo::class.java)
//                LogUtils.json("cxg", result.javaClass.canonicalName)
//                LogUtils.json("cxg", result)
//            }

            val map = ArrayMap<String, String>()
            map["Status"] = "2"
            map["RegionCode"] = "340102003001"
            map["UserCode"] = "0507"
            map["StandardCode"] = ""
            map["PageIndex"] = "1"
            map["StandardName"] = ""
            map["StandardType"] = "F"
            val map2 = ArrayMap<String, Any>()
            map2["queryCondition"] = GsonUtils.toJson(map)
            val method = "QueryStandardTask"
            val requestBody = map2.toRequestParam(methodName = method).toRequestBody()
            val exceptionHandler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                LogUtils.d(exception.message)
            }
            GlobalScope.launch(exceptionHandler) {

                val result = HttpUtils.with()
                    .url(jhUrl)
                    .post(requestBody).fromJsonArray(ListInfo::class.java)
                LogUtils.json("cxg", result.javaClass.canonicalName)
                LogUtils.json("cxg", result)
            }


        }


        btnLogin.setOnClickListener {
            Timber.tag("xx1")
            Timber.d("test")
            Timber.e(RuntimeException())

//            HttpUtils.with()
//                .url(Constants.loginUrl)
//                .addParam("username", "xyz0z0")
//                .addParam("password", "__xyz0z0")
//                .post()
//                .execute(object : EngineCallBack {
//                    override fun onError(e: Exception) {
//                        e.printStackTrace()
//                    }
//
//
//                    override fun onSuccess(result: String) {
//                        LogUtils.json("cxg", result)
//                    }
//                })
        }

        btnGetBanner.setOnClickListener {
//            Timber.tag("xx2")
            Timber.d("test2")
            Timber.e(RuntimeException())
            val netCallBack: Transform = object : Transform {


                override fun transformResponse(responseBody: ResponseBody): String {
                    return responseBody.string()
                }


            }
            HttpUtils.registerHost(Constants.bannerUrl, netCallBack)

//            HttpUtils.with()
//                .url(Constants.bannerUrl)
//                .get()
//                .execute(object : EngineCallBack {
//
//                    override fun onError(e: Exception) {
//                        e.printStackTrace()
//                    }
//
//                    override fun onSuccess(result: String) {
//                        LogUtils.json("cxg", result)
//                    }
//
//                })
        }

        btnGetUnread.setOnClickListener {
//            HttpUtils.with()
//                .url(Constants.unreadCountUrl)
//                .get()
//                .execute(object : EngineCallBack {
//
//                    override fun onError(e: Exception) {
//                        e.printStackTrace()
//                    }
//
//                    override fun onSuccess(result: String) {
//                        LogUtils.json("cxg", result)
//                    }
//                })
        }

    }

    private fun findView() {
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnGetBanner = findViewById(R.id.btnGetBanner)
        btnGetUnread = findViewById(R.id.btnGetUnread)
    }


}