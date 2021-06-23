package com.android.pay_baymax.adapter

import android.widget.TextView
import com.android.pay_baymax.room.entities.RateEntity

interface IAdapterLogicActions {
    fun convert(
        countryTextView: TextView,
        currencyTextView: TextView,
        rateItem: RateEntity
    )
}