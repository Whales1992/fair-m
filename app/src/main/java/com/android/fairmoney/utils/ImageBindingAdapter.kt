package com.android.fairmoney.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageBindingAdapter(private val picasso: Picasso?, private val url: String?, private val imageView: ImageView?) {

    fun urlNotNullOrEmpty(): Boolean{
        return (url != null && url.trim().isNotEmpty())
    }

    fun loadImageFromUrl50X50() {
        if(urlNotNullOrEmpty()){
            picasso!!
                .load(url)
                .resize(50,50)
                .centerCrop()
                .into(imageView)
        }
    }

    fun loadImageFromUrl70X70() {
        if(urlNotNullOrEmpty()){
            picasso!!
                .load(url)
                .resize(70,70)
                .centerCrop()
                .into(imageView)
        }
    }
}