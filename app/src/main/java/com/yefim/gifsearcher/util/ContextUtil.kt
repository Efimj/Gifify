package com.yefim.gifsearcher.util

import android.content.Context
import android.content.Intent
import com.yefim.gifsearcher.activity.DetailGifActivity
import com.yefim.gifsearcher.defaults.Defaults.EXTRA_GIF_ID

object ContextUtil {
    fun Context.startDetailGifActivity(id: String) {
        val intent = Intent(this, DetailGifActivity::class.java).apply {
            putExtra(EXTRA_GIF_ID, id)
        }
        this.startActivity(intent)
    }
}