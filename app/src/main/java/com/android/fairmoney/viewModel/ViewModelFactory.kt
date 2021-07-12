package com.android.fairmoney.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.fairmoney.database.room.AppDatabase

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val provideDataBase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UsersListViewModel::class.java) -> {
                UsersListViewModel(provideDataBase) as T
            }
            modelClass.isAssignableFrom(UserDetailViewModel::class.java) -> {
                UserDetailViewModel(provideDataBase) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}