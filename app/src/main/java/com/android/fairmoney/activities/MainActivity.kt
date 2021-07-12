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
import com.android.fairmoney.interfaces.IUsersAction
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

val EXTRA_USER_ID = "com.android.fairmoney.USER_ID"

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

        ViewModelProvider(this, ViewModelFactory(provideDataBase))
                .get(UsersListViewModel::class.java)
                .getUsersList(ApiCalls(provideNetworkClient))
                .observe(this, usersListObserver)
    }

    private val usersListObserver = Observer<List<User.DataBeam>> { usersList ->
        GlobalScope.launch(Dispatchers.Main){
            mActivityMainBinding
                    .progressBarCircular
                    .visibility = View.GONE

            if(usersList==null || usersList.isEmpty()){
                showEmptyTextPage()
            }else{
                hideEmptyTextPage()
                usersListAdapterSetup(usersList)
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

        ImageBindingAdapter(providePicasso, user.picture, userProfilePicture)
                .loadImageFromUrl50X50()

        userProfilePicture.setOnClickListener {
            val intent = Intent(this, UserDetailActivity::class.java).apply {
                putExtra(EXTRA_USER_ID, user.id)
            }
            startActivity(intent)
        }
    }
}