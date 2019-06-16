package ua.com.radiokot.strobe.control.view

import android.os.Bundle
import android.support.v7.widget.GridLayout
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_control.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.command.ContinuousFlashingBpmCommand
import ua.com.radiokot.strobe.command.ContinuousFlashingHzCommand
import ua.com.radiokot.strobe.command.SingleFlashCommand
import ua.com.radiokot.strobe.command.StopCommand

class ControlActivity : BaseControlActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        supportActionBar?.hide()

        root_layout.post {
            connect()
            initControls()
        }
    }

    private fun initControls() {
        flash_button.onClick {
            sendCommand(SingleFlashCommand)
        }

        flash_button.onLongClick {
            openSingleFlashScreen()
            true
        }

        stop_button.onClick {
            sendCommand(StopCommand)
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
        doAsync {
            val buttons = values.map { value ->
                Button(this@ControlActivity)
                        .apply {
                            text = value.toString()
                            tag = value
                            layoutParams = GridLayout.LayoutParams().apply {
                                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                            }
                            setOnClickListener(clickListener)
                        }
            }

            runOnUiThread {
                if (!isFinishing) {
                    buttons.forEach { layout.addView(it) }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sppConnectionManager.disconnect()
    }

    private fun openSingleFlashScreen() {
        startActivity(intentFor<SingleFlashActivity>())
    }

    companion object {
        private val BPM_VALUES = listOf<Short>(20, 30, 40, 50, 60, 70, 80, 90, 100, 110,
                120, 140, 180, 220, 280, 320, 380, 420, 510, 600, 720)

        private val HZ_VALUES = listOf<Byte>(1, 2, 3, 4, 5, 10, 12, 16)
    }
}
