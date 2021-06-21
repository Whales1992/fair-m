package com.android.pay_baymax.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.pay_baymax.ui.MainActivity

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrencyRateViewModel::class.java) -> {
                CurrencyRateViewModel(application) as T
            }
            modelClass.isAssignableFrom(CurrencyTypeViewModel::class.java) -> {
                CurrencyTypeViewModel(application) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}