package xyz.xyz0z0.modulehttputil
import com.google.gson.annotations.SerializedName


/**
 * Author: Cheng
 * Date: 2021/10/22 15:05
 * Description: xyz.xyz0z0.modulehttputil
 */
data class ListInfo(
    @SerializedName("BUILDTYPE")
    val bUILDTYPE: String,
    @SerializedName("CREATETIME")
    val cREATETIME: String,
    @SerializedName("CREATORCODE")
    val cREATORCODE: String,
    @SerializedName("CREATORNAME")
    val cREATORNAME: String,
    @SerializedName("ENDTIME")
    val eNDTIME: String,
    @SerializedName("ENDUSER")
    val eNDUSER: String,
    @SerializedName("ENDUSERCODE")
    val eNDUSERCODE: String,
    @SerializedName("FAREACODE")
    val fAREACODE: String,
    @SerializedName("INSURELISTCODE")
    val iNSURELISTCODE: String,
    @SerializedName("LISTAREACODE")
    val lISTAREACODE: String,
    @SerializedName("LISTCODE")
    val lISTCODE: String,
    @SerializedName("LISTTYPE")
    val lISTTYPE: String,
    @SerializedName("RECENTDATE")
    val rECENTDATE: String,
    @SerializedName("REGIONID")
    val rEGIONID: String,
    @SerializedName("REMARK")
    val rEMARK: String,
    @SerializedName("ROWNUMS")
    val rOWNUMS: String,
    @SerializedName("STANDARDCODE")
    val sTANDARDCODE: String,
    @SerializedName("STANDARDTYPE")
    val sTANDARDTYPE: String,
    @SerializedName("STATUS")
    val sTATUS: String,
    @SerializedName("TASKNAME")
    val tASKNAME: String,
    @SerializedName("VALIDSTATUS")
    val vALIDSTATUS: String
)