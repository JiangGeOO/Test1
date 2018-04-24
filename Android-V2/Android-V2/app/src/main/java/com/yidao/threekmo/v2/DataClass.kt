package com.yidao.threekmo.v2

import com.ijustyce.fastkotlin.irecyclerview.IResponseData
import java.util.ArrayList

/**
 * Created by deepin on 18-1-10.
 */

class ResponseModel<T> {
    var data: T? = null
    var rspCode: Int? = 0
    var rspMsg: String? = null
}

data class IndexNewsBean(var phone: String?, var name: String?, var type: Int?, var rank: Int?)

class IndexNewModel: IResponseData<IndexNewsBean> {
    override var list = ArrayList<IndexNewsBean>()
    var url: String? = null
    var strategyLinkUrl: String? = null
}

data class PayInfo(val orderNumber: String = "", val orderString: String = "",
                     val appid: String = "",
                     val w: String = "",
                     val sign: String = "",
                     val partnerid: String = "",
                     val prepayid: String = "",
                     val noncestr: String = "",
                     val timestamp: String = "")