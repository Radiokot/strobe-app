package ua.com.radiokot.strobe

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import ua.com.radiokot.strobe.command.sender.CommandSenderFactory
import ua.com.radiokot.strobe.control.logic.SppConnectionManager
import ua.com.radiokot.strobe.di.*
import ua.com.radiokot.strobe.util.ToastManager
import ua.com.radiokot.strobe.view.util.ProgressDialogFactory
import java.io.IOException
import java.net.SocketException

class App: Application() {
    private lateinit var mComponent: AppComponent

    val component: AppComponent
        get() = mComponent

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initAppComponent()
        initRxErrorHandler()
    }

    private fun initAppComponent() {
        mComponent = DaggerAppComponent.builder()
                .sppConnectionManagerModule(
                        SppConnectionManagerModule(SppConnectionManager())
                )
                .toastManagerModule(
                        ToastManagerModule(ToastManager(this))
                )
                .progressDialogFactoryModule(
                        ProgressDialogFactoryModule(ProgressDialogFactory())
                )
                .commandSenderModule(
                        CommandSenderModule(CommandSenderFactory())
                )
                .build()
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            var e = it
            if (e is UndeliverableException) {
                e = e.cause
            }
            if ((e is IOException) || (e is SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if ((e is NullPointerException) || (e is IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                        .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                        .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            Log.w("RxErrorHandler", "Undeliverable exception received, not sure what to do", e)
        }
    }
}