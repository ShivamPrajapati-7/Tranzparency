package com.example.tranzparency

import android.telephony.SmsManager

class SMSHelper {
    fun send(number: String, text: String) {
        SmsManager.getDefault().sendTextMessage(number, null, text, null, null)
    }
}