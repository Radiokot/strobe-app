package ua.com.radiokot.strobe.control.view

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_single_flash.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.command.SingleFlashCommand
import ua.com.radiokot.strobe.command.StopCommand

class SingleFlashActivity : BaseControlActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_flash)
        supportActionBar?.hide()

        connect()

        initButton()
    }

    private fun initButton() {
        flash_button.onClick {
            sendCommand(SingleFlashCommand)
        }

        flash_button.onLongClick {
            finish()
            true
        }

        stop_button.onClick {
            sendCommand(StopCommand)
        }
    }
}
