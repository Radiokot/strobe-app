package ua.com.radiokot.strobe.view.util

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes
import ua.com.radiokot.strobe.R

class ProgressDialogFactory {
    fun getProgressDialog(context: Context,
                          message: String = context.getString(R.string.loading_progress),
                          isCancelable: Boolean = false): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.isIndeterminate = true
        dialog.setMessage(message)
        dialog.setCancelable(isCancelable)
        return dialog
    }

    fun getProgressDialog(context: Context,
                          @StringRes
                          messageRes: Int,
                          isCancelable: Boolean = false): ProgressDialog {
        return getProgressDialog(context, context.getString(messageRes), isCancelable)
    }
}