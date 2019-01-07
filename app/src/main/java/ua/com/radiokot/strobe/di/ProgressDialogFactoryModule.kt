package ua.com.radiokot.strobe.di

import dagger.Module
import dagger.Provides
import ua.com.radiokot.strobe.view.util.ProgressDialogFactory
import javax.inject.Singleton

@Module
class ProgressDialogFactoryModule(
        private val factory: ProgressDialogFactory
) {
    @Provides
    @Singleton
    fun get(): ProgressDialogFactory {
        return factory
    }
}