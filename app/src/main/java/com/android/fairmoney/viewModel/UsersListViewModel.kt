package com.android.fairmoney.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.network.rectrofit.dto.UsersListDTO
import com.android.fairmoney.network.rectrofit.dto.CurrencyConversionTypes
import com.android.fairmoney.repository.ApiRepository
import com.android.fairmoney.repository.RoomRepository
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.database.room.entities.RateEntity
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

    private val mUsersList = MutableLiveData<UsersListDTO>()

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
                            override fun onCurrencyConversionRatesDone(usersListDTO: UsersListDTO) {
                                mCurrencyConversionRates.postValue(usersListDTO)
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
        fun onCurrencyConversionRatesDone(usersListDTO: UsersListDTO)
        fun onCurrencyConversionTypesDone(currencyConversionTypes: CurrencyConversionTypes)
        fun onDone()
        fun onFailure(res: String)
    }

    class GetCurrency(private val apiCalls: ApiCalls, private val callBack: CallBack): Runnable
    {
        override fun run(){
            val ratesClient = ApiRepository(apiCalls).getCurrencyRatesClient()

            val ratesResult = ratesClient.blockingSingle()

            sleep(2000)

            callBack.onCurrencyConversionRatesDone(ratesResult)

            sleep(2000)

            if(ratesResult.success)
                callBack.onDone()
            else
                callBack.onFailure(ratesResult)
        }
    }

}