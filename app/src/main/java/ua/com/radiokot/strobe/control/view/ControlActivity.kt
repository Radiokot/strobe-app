package ua.com.radiokot.strobe.control.view

import android.os.Bundle
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_control.*
import org.jetbrains.anko.onClick
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.base.BaseActivity
import ua.com.radiokot.strobe.command.ContinuousFlashingBpmCommand
import ua.com.radiokot.strobe.command.SingleFlashCommand
import ua.com.radiokot.strobe.util.ObservableTransformers

class ControlActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        connect()

        flash_button.onClick { it ->
            commandSender
                    .send(SingleFlashCommand())
                    .subscribeBy(onError = { error ->
                        toastManager.short(error.message)
                    })
                    .addTo(compositeDisposable)
        }

        bpm_flashing_button.onClick {
            commandSender
                    .send(ContinuousFlashingBpmCommand(600))
                    .subscribeBy(onError = { error ->
                        toastManager.short(error.message)
                    })
                    .addTo(compositeDisposable)
        }
    }

    private fun connect() {
        val progress = progressDialogFactory.getProgressDialog(
                this, R.string.connecting_progress, true
        )

        sppConnectionManager
                .connect()
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

    private fun onConnectionError(error: Throwable) {
        error.printStackTrace()
        toastManager.short(error.message)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        sppConnectionManager.disconnect()
    }
}
