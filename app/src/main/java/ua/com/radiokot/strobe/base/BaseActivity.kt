package ua.com.radiokot.strobe.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import ua.com.radiokot.strobe.App
import ua.com.radiokot.strobe.command.sender.CommandSender
import ua.com.radiokot.strobe.control.logic.SppConnectionManager
import ua.com.radiokot.strobe.util.ToastManager
import ua.com.radiokot.strobe.view.util.ProgressDialogFactory
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject
    protected lateinit var sppConnectionManager: SppConnectionManager

    @Inject
    protected lateinit var toastManager: ToastManager

    @Inject
    protected lateinit var progressDialogFactory: ProgressDialogFactory

    @Inject
    protected lateinit var commandSender: CommandSender

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (application as? App)?.component?.inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}