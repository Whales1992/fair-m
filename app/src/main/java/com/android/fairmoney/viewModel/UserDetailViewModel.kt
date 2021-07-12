package com.android.fairmoney.viewModel

import androidx.lifecycle.*
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.repository.ApiRepository
import com.android.fairmoney.repository.RoomRepository
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.database.room.entities.UserDetailEntity
import com.android.fairmoney.database.room.entities.UserEntity
import com.android.fairmoney.models.User
import com.android.fairmoney.interfaces.IApiCallBackGetUser
import com.android.fairmoney.interfaces.IApiCallBackGetUserDetail
import com.android.fairmoney.models.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class UserDetailsViewModel(appDatabase: AppDatabase) : ViewModel()
{
    private val mUsersListMutableLiveData: MutableLiveData<List<User.DataBeam>> = MutableLiveData()
    private val mRoomRepository = RoomRepository(appDatabase)

    fun getUserDetails(userId: String, apiCalls: ApiCalls): LiveData<List<User.DataBeam>>
    {
        try {
            viewModelScope
                    .launch {
                withContext(Dispatchers.IO) {

                    if(mRoomRepository.loadUsersList().isNotEmpty()){
                        mUsersListMutableLiveData.postValue(mRoomRepository.loadUsersList().map { it.toUser() })
                    }

                    Thread(GetUserDetail(userId, apiCalls, object : IApiCallBackGetUserDetail {
                        override fun onDone(userDetail: UserDetail) {
                            mRoomRepository

                            mUsersListMutableLiveData.postValue(userEntityList.map { it.toUser() })
                        }
                    }))
                            .start()
                }
            }
        } catch (ex: IllegalThreadStateException) {
            ex.printStackTrace()

            if(mUsersListMutableLiveData.value == null || mUsersListMutableLiveData.value!!.isEmpty()){
                mUsersListMutableLiveData.postValue(mRoomRepository.loadUsersList().map { it.toUser() })
            }

        }

        return mUsersListMutableLiveData
    }

    class GetUserDetail(private val userId : String, private val apiCalls: ApiCalls, private val callBackGetUser: IApiCallBackGetUser): Runnable
    {
        override fun run(){
            val getUserDetailResult = ApiRepository(apiCalls).getUsersDetailApiRepository(userId).blockingSingle()

            if(getUserDetailResult!=null)
            {
                callBackGetUser.onDoneGetUserDetail(getUserDetailResult);
            }
        }
    }

}