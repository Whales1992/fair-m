package com.android.fairmoney.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.fairmoney.R
import com.android.fairmoney.interfaces.IUsersAction
import com.android.fairmoney.models.User

class UsersListAdapter(private val userList: ArrayList<User.DataBeam>, private val iUsersAction : IUsersAction) :
        RecyclerView.Adapter<UsersListAdapter.UsersListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListAdapterViewHolder
    {
            return UsersListAdapterViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: UsersListAdapterViewHolder, position: Int)
    {
        val user: User.DataBeam = userList[position]
        holder.bind(user, iUsersAction)
    }

    override fun getItemCount(): Int {
        return userList.size;
    }

    class UsersListAdapterViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_user_layout, parent, false))
    {
        private var profilePictureCardView: CardView = itemView.findViewById(R.id.card_view_profile_picture)
        private var userProfilePicture: ImageView = itemView.findViewById(R.id.image_view_profile_picture)
        private var userProfileName: TextView = itemView.findViewById(R.id.text_view_profile_name)

        fun bind(user: User.DataBeam, iUsersAction: IUsersAction) {
            iUsersAction.renderItem(profilePictureCardView, userProfileName, userProfilePicture, user)
        }
    }
}