package me.kariot.wallpaperapp.utils

import android.app.Activity
import android.app.AlertDialog
import me.kariot.wallpaperapp.R

object Dialogs {
    fun askConfirmation(activity: Activity, onClickYes: () -> Unit, onClickNo: () -> Unit) {
        val builder = AlertDialog.Builder(activity).apply {
            setTitle(context.getString(R.string.update_lock_screen_dialog_title))
            setMessage(context.getString(R.string.update_lock_screen_dialog_message))
            setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                onClickYes()
                dialog.dismiss()
            }
            setNegativeButton(context.getString(R.string.no)) { dialog, which ->
                onClickNo()
                dialog.dismiss()
            }
            setNeutralButton(android.R.string.cancel, null)
        }.show()
    }
}