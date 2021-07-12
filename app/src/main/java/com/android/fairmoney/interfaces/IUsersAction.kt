package com.android.fairmoney.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.fairmoney.models.User

interface IUsersAction {
    fun renderItem(
            profilePictureCardView: CardView,
            userProfileName: TextView,
            userProfilePicture: ImageView,
            user: User.DataBeam
    )
}