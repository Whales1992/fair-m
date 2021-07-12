package com.android.fairmoney.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.repository.ApiRepository
import com.android.fairmoney.repository.RoomRepository
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.database.room.entities.UserEntity
import com.android.fairmoney.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.util.NoSuchElementException
import kotlin.collections.ArrayList

class UsersListViewModel(appDatabase: AppDatabase) : ViewModel()
{
    private val TAG = this.javaClass.simpleName
    private val mUsersListMutableLiveData: MutableLiveData<List<User.DataBeam>> = MutableLiveData()
    private val mRoomRepository = RoomRepository(appDatabase)

    fun getUsersList(apiCalls: ApiCalls): LiveData<List<User.DataBeam>>
    {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    populateLocallyStoredData()

                    ApiRepository(apiCalls).getUsersApiRepository().enqueue(object : Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if(response.isSuccessful && response.body()!=null){
                                val userEntityList = ArrayList<UserEntity>()

                                response.body()!!.data.forEach { userEntityList.add(UserEntity.from(it)) }

                                mUsersListMutableLiveData.postValue(userEntityList.map { it.toUser() })

                                updateLocalStorage(userEntityList)
                            }
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.d(TAG, "$t")
                        }
                    })
                }
            }
        } catch (ex: IllegalThreadStateException) {
            ex.printStackTrace()
            populateLocallyStoredData()
        } catch (ex: NoSuchElementException){
            ex.printStackTrace()
            populateLocallyStoredData()
        }catch (ex: RuntimeException){
            ex.printStackTrace()
            populateLocallyStoredData()
        }

        return mUsersListMutableLiveData
    }

    private fun updateLocalStorage(userEntityList: ArrayList<UserEntity>){
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                mRoomRepository.updateAllUsers(userEntityList)
            }
        }
    }

    private fun populateLocallyStoredData(){
        try {
            viewModelScope.launch{
                withContext(Dispatchers.IO) {
                    val usersList = mRoomRepository.loadUsersList()
                    if(usersList!=null) mUsersListMutableLiveData.postValue(usersList.map { it.toUser() })
                }
            }
        }catch (ex: RuntimeException){
            ex.printStackTrace()
            Log.e(TAG, "$ex")
        }
    }

}