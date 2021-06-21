package com.android.pay_baymax.adapter

import android.widget.TextView
import com.android.pay_baymax.room.entities.RateEntity

interface IAdapterLogicActions {
    fun convert(
        country_textview: TextView,
        currency_textview: TextView,
        rateItem: RateEntity
    )
}