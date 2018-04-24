package com.yidao.threekmo.v2.model

import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.IResponseData
import java.util.ArrayList

/**
 * Created by deepin on 17-12-20.
 */

abstract class HttpResultList<T> : IResponseData<T>, HttpResponse()

open class HttpResponse {

}