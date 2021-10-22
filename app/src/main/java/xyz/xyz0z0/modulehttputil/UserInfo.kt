package xyz.xyz0z0.modulehttputil
import com.google.gson.annotations.SerializedName


/**
 * Author: Cheng
 * Date: 2021/10/22 14:21
 * Description: xyz.xyz0z0.modulehttputil
 */


data class UserInfo(
    @SerializedName("AddRess")
    val addRess: Any,
    @SerializedName("ComCode")
    val comCode: String,
    @SerializedName("IsNewPwd")
    val isNewPwd: String,
    @SerializedName("OrgName")
    val orgName: String,
    @SerializedName("PassWord")
    val passWord: String,
    @SerializedName("PassWordExpIreDate")
    val passWordExpIreDate: String,
    @SerializedName("PassWordSetDate")
    val passWordSetDate: String,
    @SerializedName("Phone")
    val phone: Any,
    @SerializedName("UserCode")
    val userCode: String,
    @SerializedName("UserEName")
    val userEName: Any,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("ValidStatus")
    val validStatus: String,
    @SerializedName("ViaImg")
    val viaImg: String
)