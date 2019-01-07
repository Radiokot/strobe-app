package ua.com.radiokot.strobe.di

import dagger.Component
import ua.com.radiokot.strobe.base.BaseActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    SppConnectionManagerModule::class,
    ToastManagerModule::class
])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}