/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.fairmoney.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.fairmoney.adapters.IUsersAction
import com.android.fairmoney.adapters.UsersListAdapter
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.databinding.ActivityMainBinding
import com.android.fairmoney.models.User
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.utils.ImageBindingAdapter
import com.android.fairmoney.viewModel.UsersListViewModel
import com.android.fairmoney.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), IUsersAction {

    private lateinit var mActivityMainBinding: ActivityMainBinding

    @Inject
    lateinit var provideNetworkClient: Retrofit

    @Inject
    lateinit var provideDataBase: AppDatabase

    @Inject
    lateinit var providePicasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mActivityMainBinding.root
        setContentView(view)

        mActivityMainBinding
                .contentLoadingProgressBar
                .show()

        ViewModelProvider(this, ViewModelFactory(provideDataBase))
                .get(UsersListViewModel::class.java)
                .getUsersList(ApiCalls(provideNetworkClient))
                .observe(this, usersListObserver)
    }

    private val usersListObserver = Observer<List<User.DataBeam>> { usersList ->
        runOnUiThread {
            mActivityMainBinding.contentLoadingProgressBar.hide()

            try {
                if(usersList!=null && usersList.isNotEmpty()){
                    hideEmptyTextPage()
                    usersListAdapterSetup(usersList)
                }else{
                    showEmptyTextPage()
                }
            } catch (ex: Exception) {
                showEmptyTextPage()
            }
        }
    }

    private fun usersListAdapterSetup(usersList: List<User.DataBeam>) {
        mActivityMainBinding
                .recyclerViewUsers
                .adapter = UsersListAdapter(usersList as ArrayList<User.DataBeam>, this)

        mActivityMainBinding
                .recyclerViewUsers
                .layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun showEmptyTextPage(){
        mActivityMainBinding
                .textViewEmpty
                .visibility = View.VISIBLE
    }

    private fun hideEmptyTextPage(){
        mActivityMainBinding
                .textViewEmpty
                .visibility = View.GONE
    }

    override fun renderItem(profilePictureCardView: CardView, userProfileName: TextView, userProfilePicture: ImageView, user: User.DataBeam) {
        val fullName = "${user.title}. ${user.lastName} ${user.firstName}"
        userProfileName.text = fullName

        ImageBindingAdapter(providePicasso, user.picture)
                .loadImageFromUrl(userProfilePicture)

        userProfilePicture.setOnClickListener {
            val intent = Intent(this, DisplayMessageActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            startActivity(intent)
        }
    }
}