package xyz.xyz0z0.modulehttputil

/**
 * Author: Cheng
 * Date: 2021/4/15 9:28
 * Description: 金禾 GYWebService 服务地址
 * Version: 1.0.0
 */
object AbsJHService {

    fun getWebServiceParam(methodName: String, params: Map<String, Any>): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append(methodName.toStart())
        for ((key, value) in params) {
            stringBuffer.append(key.toStart()).append(value).append(key.toEnd())
        }
        stringBuffer.append(methodName.toEnd())
        val param =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
                    "<soapenv:Header/>" +
                    "<soapenv:Body>" +
                    stringBuffer.toString() +
                    "</soapenv:Body>" +
                    "</soapenv:Envelope>"
        return param
    }


}