package com.android.pay_baymax.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.pay_baymax.room.AppDatabase

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val provideDataBase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrencyRateViewModel::class.java) -> {
                CurrencyRateViewModel(provideDataBase) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}