package com.vb.store.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vb.store.R


fun ImageView.loadImage(url: String?, placeHolder: Int = -1) {
    val defaultPlaceHolderId = if (placeHolder == -1) {
        R.drawable.ic_electronics
    } else {
        placeHolder
    }
    Glide.with(this.context)
        .load(url)
        .placeholder(defaultPlaceHolderId)
        .error(defaultPlaceHolderId)
        .into(this)
}
