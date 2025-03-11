package com.example.project_aksis.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.project_aksis.R


fun ImageView.downloadFromUrl(url : String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder)
        .dontAnimate()

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}


fun placeHolderProgressBar(context : Context) : CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 3f
        centerRadius = 19f
        start()
    }
}

@BindingAdapter("android:bindWithDownloadUrl")
fun bindWithDownloadUrl(view : ImageView, url : String?) {
    view.downloadFromUrl(url, placeHolderProgressBar(view.context))
}

