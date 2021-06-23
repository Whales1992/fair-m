package com.android.pay_baymax.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.android.pay_baymax.rectrofit.ApiCalls
import com.android.pay_baymax.rectrofit.dto.CurrencyConversionRates
import com.android.pay_baymax.rectrofit.dto.CurrencyConversionTypes
import com.android.pay_baymax.repository.ApiRepository
import com.android.pay_baymax.repository.RoomRepository
import com.android.pay_baymax.room.AppDatabase
import com.android.pay_baymax.room.entities.RateEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList

class CurrencyRateViewModel(appDatabase: AppDatabase) : ViewModel()
{
    private val responseMutableLiveData: MutableLiveData<ResponseObjectMapper> = MutableLiveData()
    private val keepUnitAndSelectedMutableLiveData: MutableLiveData<Map<String, Any>> = MutableLiveData(mapOf("unit" to 0.0, "selectedPosition" to 0))

    private val mCurrencyConversionRates = MutableLiveData<CurrencyConversionRates>()
    private val mCurrencyConversionTypes = MutableLiveData<CurrencyConversionTypes>()

    private val dbRepository = RoomRepository(appDatabase)

    fun getCurrencyRatesAndTypes(apiCalls: ApiCalls, sharedPreference: SharedPreferences): LiveData<ResponseObjectMapper>
    {
        try {
            viewModelScope.launch {
                val currentTime = Calendar.getInstance().time.time

                if((sharedPreference.getLong("last_call", 0) + (30 * 60 * 1000)) <= currentTime){
                    sharedPreference.edit().putLong("last_call", Calendar.getInstance().time.time).apply()

                    withContext(Dispatchers.IO) {

                        Thread(GetCurrency(apiCalls, object : CallBack{
                            override fun onCurrencyConversionRatesDone(currencyConversionRates: CurrencyConversionRates) {
                                mCurrencyConversionRates.postValue(currencyConversionRates)
                            }

                            override fun onCurrencyConversionTypesDone(currencyConversionTypes: CurrencyConversionTypes) {
                                mCurrencyConversionTypes.postValue(currencyConversionTypes)
                            }

                            override fun onDone() {
                                try {
                                    responseMutableLiveData.postValue(ResponseObjectMapper(true, marshallData()))
                                }catch (ex:Exception){
                                    ex.printStackTrace()
                                }
                            }

                            override fun onFailure(res: String) {
                                responseMutableLiveData.postValue(ResponseObjectMapper(false, null))
                            }

                        })).start()
                    }
                }else{
                    withContext(Dispatchers.IO) {
                        responseMutableLiveData.postValue(ResponseObjectMapper(true, dbRepository.loadRateList() as ArrayList<RateEntity>))
                    }
                }
            }
        } catch (ex: IllegalThreadStateException) {
            ex.printStackTrace()
        }

        return responseMutableLiveData
    }

    interface CallBack{
        fun onCurrencyConversionRatesDone(currencyConversionRates: CurrencyConversionRates)
        fun onCurrencyConversionTypesDone(currencyConversionTypes: CurrencyConversionTypes)
        fun onDone()
        fun onFailure(res: String)
    }

    private fun marshallData() : List<RateEntity> {
        val listOfRatesEntity = ArrayList<RateEntity>()

        val mRates = mCurrencyConversionRates.value!!.quotes
        val mTypes = mCurrencyConversionTypes.value!!.currencies

        val countryKeys = ArrayList<String>()
        mTypes.keys.toList().forEach {
            countryKeys.add(it.replaceFirst(mCurrencyConversionRates.value!!.source, ""))
        }

        for (key in countryKeys) {
            val mKey = "${mCurrencyConversionRates.value!!.source}${key}"
            if (mRates.containsKey(mKey)) {
                val fullName = mTypes[key]
                listOfRatesEntity.add(RateEntity(key, mRates[mKey] ?: 1.0, fullName))
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.updateAllRate(listOfRatesEntity)
        }

        return listOfRatesEntity
    }

    class GetCurrency(private val apiCalls: ApiCalls, private val callBack: CallBack): Runnable
    {
        override fun run(){
            val ratesClient = ApiRepository(apiCalls).getCurrencyRatesClient()
            val typesClient = ApiRepository(apiCalls).getCurrencyTypeClient()

            val ratesResult = ratesClient.blockingSingle()

            sleep(2000)

            val typesResult = typesClient.blockingSingle()

            callBack.onCurrencyConversionRatesDone(ratesResult)
            callBack.onCurrencyConversionTypesDone(typesResult)

            sleep(2000)

            if(ratesResult.success && typesResult.success)
                callBack.onDone()
        }
    }

    //Note that when -1 is parsed, it uses the previous value of such param
    fun keepUnitAndSelected(unit: Double, selectedPosition: Int){
        val mUnit = if(unit==(-1.0)) keepUnitAndSelectedMutableLiveData.value!!["unit"] as Double else unit
        val mSelectedPosition = if(selectedPosition==(-1)) keepUnitAndSelectedMutableLiveData.value!!["selectedPosition"] as Int else selectedPosition
        val marshallValue = mapOf("unit" to mUnit, "selectedPosition" to mSelectedPosition)
        keepUnitAndSelectedMutableLiveData.value = marshallValue
    }

    fun retrieveKeptUnitAndSelected(): MutableLiveData<Map<String, Any>> = keepUnitAndSelectedMutableLiveData
}