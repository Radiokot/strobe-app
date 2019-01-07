package ua.com.radiokot.strobe

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import ua.com.radiokot.strobe.control.logic.SppConnectionManager
import ua.com.radiokot.strobe.di.AppComponent
import ua.com.radiokot.strobe.di.DaggerAppComponent
import ua.com.radiokot.strobe.di.SppConnectionManagerModule
import ua.com.radiokot.strobe.di.ToastManagerModule
import ua.com.radiokot.strobe.util.ToastManager

class App: Application() {
    private lateinit var mComponent: AppComponent

    val component: AppComponent
        get() = mComponent

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initAppComponent()
    }

    private fun initAppComponent() {
        mComponent = DaggerAppComponent.builder()
                .sppConnectionManagerModule(
                        SppConnectionManagerModule(SppConnectionManager())
                )
                .toastManagerModule(
                        ToastManagerModule(ToastManager(this))
                )
                .build()
    }
}