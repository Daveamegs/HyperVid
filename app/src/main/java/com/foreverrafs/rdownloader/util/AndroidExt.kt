package com.foreverrafs.rdownloader.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.foreverrafs.downloader.model.DownloadInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.File

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}


fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

fun Context.shareFile(path: String, packageName: String = "") {
    val context = this

    val uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        File(path)
    )

    val videoShare = Intent(Intent.ACTION_SEND).apply {
        if (packageName.isNotEmpty())
            setPackage(packageName)

        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "*/*"
    }

    try {
        context.startActivity(videoShare)
    } catch (exception: ActivityNotFoundException) {
        Timber.e("$packageName is not installed")
    }
}

fun List<DownloadInfo>.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}

fun String.fromJson(): List<DownloadInfo> {
    val gson = Gson()
    val type = object : TypeToken<List<DownloadInfo>>() {}.type

    return gson.fromJson(this, type)
}