package xyz.xyz0z0.modulehttputil

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import xyz.xyz0z0.httputil.HttpUtilException
import java.lang.reflect.Type

fun Map<String, Any>.toRequestParam(methodName: String): String {
    val stringBuffer = StringBuffer()
    stringBuffer.append(methodName.toStart())
    for ((key, value) in this) {
        stringBuffer.append(key.toStart()).append(value).append(key.toEnd())
    }
    stringBuffer.append(methodName.toEnd())
    return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
            "<soapenv:Header/>" +
            "<soapenv:Body>" +
            stringBuffer.toString() +
            "</soapenv:Body>" +
            "</soapenv:Envelope>"
}

fun String.toRequestBody(): RequestBody = this.toRequestBody("text/xml;charset=utf-8".toMediaType())

fun <T> String.fromJsonObject(clazz: Class<T>): T {
    val type: Type = ParameterizedTypeImpl(CustomerSystemBaseResponse::class.java, arrayOf(clazz))
    val baseResponse: CustomerSystemBaseResponse<T> = GsonUtils.fromJson(this, type)
    if (baseResponse.success()) {
        return baseResponse.data
    } else {
        throw HttpUtilException(-1, baseResponse.getSafeMsg())
    }
}

fun <T> String.fromJsonArray(clazz: Class<T>): List<T> {
    val listType: Type = ParameterizedTypeImpl(MutableList::class.java, arrayOf(clazz))
    val type: Type = ParameterizedTypeImpl(CustomerSystemBaseResponse::class.java, arrayOf(listType))
    val baseResponse: CustomerSystemBaseResponse<List<T>> = GsonUtils.fromJson(this, type)
    if (baseResponse.success()) {
        return baseResponse.data
    } else {
        throw HttpUtilException(-1, baseResponse.getSafeMsg())
    }
}


fun String.toStart(): String {
    return "<tem:$this>"
}

fun String.toEnd(): String {
    return "</tem:$this>"
}

data class CustomerSystemBaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("errMsg")
    val errMsg: String?,
    @SerializedName("requestCode")
    val requestCode: String?
) {

    fun getSafeCode(): String {
        return requestCode ?: "401"
    }

    fun getSafeMsg(): String {
        return errMsg ?: "Empty error msg"
    }

    fun success(): Boolean {
        return "401" == requestCode
    }

}