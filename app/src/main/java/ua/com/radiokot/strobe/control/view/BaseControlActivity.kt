package ua.com.radiokot.strobe.control.view

import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.base.BaseActivity
import ua.com.radiokot.strobe.command.Command
import ua.com.radiokot.strobe.util.ObservableTransformers
import java.io.IOException

abstract class BaseControlActivity: BaseActivity() {
    protected open fun connect() {
        val progress = progressDialogFactory.getProgressDialog(
                this, R.string.connecting_progress, true, false
        )

        sppConnectionManager
                .getConnection()
                .compose(ObservableTransformers.defaultSchedulersSingle())
                .doOnSubscribe { progress.show() }
                .subscribeBy(
                        onSuccess = {
                            progress.dismiss()
                        },
                        onError = this::onConnectionError
                )
                .addTo(compositeDisposable)

        progress.setOnCancelListener {
            finish()
        }
    }

    protected open fun onConnectionError(error: Throwable) {
        error.printStackTrace()
        toastManager.short(error.message)
        finish()
    }

    protected open fun sendCommand(command: Command) {
        commandSender
                .send(command)
                .compose(ObservableTransformers.defaultSchedulersCompletable())
                .subscribeBy(
                        onError = this::onCommandSendError
                )
                .addTo(compositeDisposable)
    }

    protected open fun onCommandSendError(error: Throwable) {
        if (error is IOException) {
            toastManager.short(R.string.error_connection_lost)
            finish()
        } else {
            toastManager.short(error.message)
        }
    }
}