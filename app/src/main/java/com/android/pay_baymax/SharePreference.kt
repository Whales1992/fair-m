package com.android.pay_baymax

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.*

class SharePreference (private val context: Context){
    var sharedpreferences: SharedPreferences = context.getSharedPreferences("com.android.pay_baymax", Context.MODE_PRIVATE)

    fun getLastRequest():Long{
        Log.e("@ApplicationClass", "$sharedpreferences")
        return sharedpreferences.getLong("last_time", 0)
    }

    fun setLastRequest(){
        sharedpreferences.edit().putLong("last_time", Calendar.getInstance().time.time).apply()
    }
}