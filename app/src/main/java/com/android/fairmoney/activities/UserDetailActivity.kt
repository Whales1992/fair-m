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

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.databinding.ActivityUserDetailBinding
import com.android.fairmoney.models.UserDetail
import com.android.fairmoney.network.rectrofit.ApiCalls
import com.android.fairmoney.utils.ImageBindingAdapter
import com.android.fairmoney.viewModel.UserDetailViewModel
import com.android.fairmoney.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.StringBuilder
import javax.inject.Inject

class UserDetailActivity : DaggerAppCompatActivity(){

    private lateinit var mActivityUserDetailBinding: ActivityUserDetailBinding

    @Inject
    lateinit var provideNetworkClient: Retrofit

    @Inject
    lateinit var provideDataBase: AppDatabase

    @Inject
    lateinit var providePicasso: Picasso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivityUserDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = mActivityUserDetailBinding.root
        setContentView(view)

        val userId = intent.getStringExtra(EXTRA_USER_ID)

        ViewModelProvider(this, ViewModelFactory(provideDataBase))
                .get(UserDetailViewModel::class.java)
                .getUserDetail(userId!!, ApiCalls(provideNetworkClient))
                .observe(this, userDetailObserver)
    }

    private val userDetailObserver = Observer<UserDetail> { userDetail ->
        GlobalScope.launch(Dispatchers.Main){
            mActivityUserDetailBinding.progressBarCircular.visibility = View.GONE

            if(userDetail==null){
                showErrorPage()
            } else {
                hideErrorPage()
                setUI(userDetail)
            }
        }
    }

    private fun setUI(userDetail: UserDetail){
        val fullName = StringBuilder()
                .append(userDetail.lastName)
                .append(" ")
                .append(userDetail.firstName)
                .toString()

        val fullAddress = StringBuilder()
                .append(userDetail.location.street)
                .append(", ")
                .append(userDetail.location.city)
                .append(" ")
                .append(userDetail.location.state)
                .append(", ")
                .append(userDetail.location.country)
                .toString()

        mActivityUserDetailBinding.textViewFullProfileName.text = fullName
        mActivityUserDetailBinding.textViewEmail.text = userDetail.email
        mActivityUserDetailBinding.textViewGender.text = userDetail.gender
        mActivityUserDetailBinding.textViewPhone.text = userDetail.phone
        mActivityUserDetailBinding.textViewAddress.text = fullAddress

        ImageBindingAdapter(providePicasso, userDetail.picture, mActivityUserDetailBinding.imageViewFullProfilePicture)
                .loadImageFromUrl70X70()
    }

    private fun showErrorPage(){
        mActivityUserDetailBinding.textViewErrorOccurred.visibility = View.VISIBLE
    }

    private fun hideErrorPage(){
        mActivityUserDetailBinding.textViewErrorOccurred.visibility = View.GONE
    }
}