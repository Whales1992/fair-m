package com.android.pay_baymax.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.android.pay_baymax.rectrofit.ApiCalls
import com.android.pay_baymax.rectrofit.IResponse
import com.android.pay_baymax.rectrofit.dto.CurrencyConversionTypes
import com.android.pay_baymax.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyTypeViewModel(application: Application) : AndroidViewModel(application)
{
    private val responseMutableLiveData: MutableLiveData<ResponseObjectMapper> = MutableLiveData()

//    fun getCurrencyTypes(apiCalls: ApiCalls): LiveData<ResponseObjectMapper>
//    {
//        try {
//            viewModelScope.launch {
//                withContext(Dispatchers.IO) {
//                    Thread(GetCurrencyTypes(apiCalls, responseMutableLiveData)).start()
//                }
//            }
//        } catch (ex: IllegalThreadStateException) {
//            ex.printStackTrace()
//        }
//
//        return responseMutableLiveData
//    }

//    class GetCurrencyTypes(private val apiCalls: ApiCalls, private val responseMutableLiveData: MutableLiveData<ResponseObjectMapper>): Runnable
//    {
//        override fun run(){
//            ApiRepository(apiCalls).getCurrencyTypes(object : IResponse<CurrencyConversionTypes> {
//                override fun onSuccess(res: CurrencyConversionTypes) {
//                    responseMutableLiveData.postValue(ResponseObjectMapper (true, res))
//                }
//
//                override fun onFailure(res: String) {
//                    responseMutableLiveData.postValue(ResponseObjectMapper (false, res))
//                }
//
//                override fun onNetworkError(res: String) {
//                    responseMutableLiveData.postValue(ResponseObjectMapper (false, res))
//                }
//            })
//        }
//    }

//    class CurrencyRates(private val responseMutableLiveData: MutableLiveData<ResponseObjectMapper>): Runnable
//    {
//        override fun run(){
//            responseMutableLiveData.postValue(ResponseObjectMapper(true, MovieRepository.loadRateList()))
//        }
//    }
}