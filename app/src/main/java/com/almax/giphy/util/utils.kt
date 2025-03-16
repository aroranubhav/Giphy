package com.almax.giphy.util

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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

fun View.updateVisibility(state: Boolean) {
    if (state) this.visibility = VISIBLE else this.visibility = GONE
}

fun shareGif(context: Context, message: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            message
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}