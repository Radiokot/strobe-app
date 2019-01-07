package ua.com.radiokot.strobe.control.view

import android.os.Bundle
import android.support.v7.widget.GridLayout
import android.view.View
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_control.*
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.base.BaseActivity
import ua.com.radiokot.strobe.command.Command
import ua.com.radiokot.strobe.command.ContinuousFlashingBpmCommand
import ua.com.radiokot.strobe.command.ContinuousFlashingHzCommand
import ua.com.radiokot.strobe.command.SingleFlashCommand
import ua.com.radiokot.strobe.util.ObservableTransformers
import java.io.IOException

class ControlActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        connect()

        initControls()
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

    private fun initControls() {
        flash_button.onClick {
            sendCommand(SingleFlashCommand())
        }

        createBpmButtons()
        createHzButtons()
    }

    private fun createBpmButtons() {
        val clickListener = View.OnClickListener { view ->
            val bpm = view.tag as? Short
                    ?: return@OnClickListener

            sendCommand(ContinuousFlashingBpmCommand(bpm))
        }

        fillLayoutWithButtons(bpm_layout, BPM_VALUES, clickListener)
    }

    private fun createHzButtons() {
        val clickListener = View.OnClickListener { view ->
            val hz = view.tag as? Byte
                    ?: return@OnClickListener

            sendCommand(ContinuousFlashingHzCommand(hz))
        }

        fillLayoutWithButtons(hz_layout, HZ_VALUES, clickListener)
    }

    private fun fillLayoutWithButtons(layout: GridLayout,
                                      values: List<Any>,
                                      clickListener: View.OnClickListener) {
        values.forEach { value ->
            layout.button {
                text = value.toString()
                tag = value
                layoutParams = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                setOnClickListener(clickListener)
            }
        }
    }

    private fun sendCommand(command: Command) {
        commandSender
                .send(command)
                .compose(ObservableTransformers.defaultSchedulersCompletable())
                .subscribeBy(
                        onError = this::onCommandSendError
                )
                .addTo(compositeDisposable)
    }

    private fun onCommandSendError(error: Throwable) {
        if (error is IOException) {
            toastManager.short(R.string.error_connection_lost)
            finish()
        } else {
            toastManager.short(error.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sppConnectionManager.disconnect()
    }

    companion object {
        private val BPM_VALUES = listOf<Short>(20, 30, 40, 60, 70, 80, 90, 100,
                120, 180, 220, 280, 320, 380, 420, 600, 720)

        private val HZ_VALUES = listOf<Byte>(1, 2, 3, 4, 5, 10, 12, 16)
    }
}
