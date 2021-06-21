package com.android.pay_baymax.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android.pay_baymax.R
import com.android.pay_baymax.room.entities.RateEntity

class GridViewAdapter(
        private val context: Context,
        private val list: List<RateEntity>,
        private val iAdapterActions: IAdapterLogicActions) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rateItem = list[position]
        val view = LayoutInflater.from(context).inflate(R.layout.item_gridview, null)

        val countryTextView: TextView = view.findViewById(R.id.country_textview)
        val currencyTextView: TextView = view.findViewById(R.id.currency_textview)

        iAdapterActions.convert(countryTextView, currencyTextView, rateItem)
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}