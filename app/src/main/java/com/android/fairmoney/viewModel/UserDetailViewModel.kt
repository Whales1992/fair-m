package com.android.fairmoney.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.repository.ApiRepository
import com.android.fairmoney.repository.RoomRepository
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.database.room.entities.UserDetailEntity
import com.android.fairmoney.models.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserDetailViewModel(appDatabase: AppDatabase) : ViewModel()
{
    val TAG: String = this.javaClass.simpleName

    private val mUserDetailMutableLiveData: MutableLiveData<UserDetail> = MutableLiveData()
    private val mRoomRepository = RoomRepository(appDatabase)

    fun getUserDetail(userId: String, apiCalls: ApiCalls): LiveData<UserDetail>
    {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    populateLocallyStoredData(userId)

                    ApiRepository(apiCalls).getUsersDetailApiRepository(userId).enqueue(object : Callback<UserDetail> {
                        override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                            if(response.isSuccessful && response.body()!=null){
                                updateLocalStorage(response.body()!!)
                                mUserDetailMutableLiveData.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                            Log.e(TAG, "$t")
                        }
                    })
                }
            }
        } catch (ex: IllegalThreadStateException) {
            ex.printStackTrace()
            populateLocallyStoredData(userId)
        }catch (ex: RuntimeException){
            ex.printStackTrace()
            populateLocallyStoredData(userId)
        }

        return mUserDetailMutableLiveData
    }

    private fun updateLocalStorage(body: UserDetail){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val userDetailEntity = UserDetailEntity.from(body)
                mRoomRepository.updateUserDetail(userDetailEntity)
                mRoomRepository.updateUserLocation(userDetailEntity.location)
            }
        }
    }

    private fun populateLocallyStoredData(userId: String){
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val userDetailAndLocation = mRoomRepository.loadUserDetailByUserId(userId)
                    Log.d(TAG, "$userDetailAndLocation")

                    if(userDetailAndLocation!=null) mUserDetailMutableLiveData.postValue(userDetailAndLocation.userDetailEntity.toUserDetail())
                }
            }
        }catch (ex:RuntimeException){
            ex.printStackTrace()
            Log.d(TAG, "$ex")
        }
    }
}