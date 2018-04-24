package com.ijustyce.fastkotlin.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsMessage

/**
 * Created by deepin on 17-12-25.
 */

class SmsUtils {

    interface OnSms {
        fun onReceive(smsMessage: SmsMessage)
    }

    private var broadcastReceiver: BroadcastReceiver? = null

    fun initSmsReceiver(context: Context, onSms: OnSms) :SmsUtils{
        val filter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?: return
                val messages = getMessages(intent)
                for (sms in messages) {
                    sms ?: continue
                    onSms.onReceive(sms)
                }
            }
        }
        context.registerReceiver(broadcastReceiver, filter)
        return this
    }

    fun getMessages(intent: Intent): Array<SmsMessage?> {
        val messages = intent.getSerializableExtra("pdus") as Array<Any>
        val pduObjs = arrayOfNulls<ByteArray>(messages.size)

        for (i in messages.indices) {
            pduObjs[i] = messages[i] as ByteArray
        }

        val pdus = arrayOfNulls<ByteArray>(pduObjs.size)
        val pduCount = pdus.size
        val msgs = arrayOfNulls<SmsMessage>(pduCount)
        for (i in 0 until pduCount) {
            pdus[i] = pduObjs[i]
            msgs[i] = SmsMessage.createFromPdu(pdus[i])
        }
        return msgs
    }

    fun destroy(context: Context) {
        context.unregisterReceiver(broadcastReceiver)
        broadcastReceiver = null
    }
}