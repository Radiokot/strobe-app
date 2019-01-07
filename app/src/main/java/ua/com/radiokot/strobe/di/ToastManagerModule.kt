package ua.com.radiokot.strobe.di

import dagger.Component
import dagger.Module
import dagger.Provides
import ua.com.radiokot.strobe.util.ToastManager
import javax.inject.Singleton

@Module
class ToastManagerModule(
        private val manager: ToastManager
) {
    @Provides
    @Singleton
    fun get(): ToastManager {
        return manager
    }
}