package com.almax.giphy.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun ImageView.tint(context: Context, @ColorRes color: Int) {
    DrawableCompat.setTint(
        this.drawable,
        ContextCompat.getColor(context, color)
    )
}

fun getEmoji(unicode: Int): String {
    return String(Character.toChars(unicode))
}