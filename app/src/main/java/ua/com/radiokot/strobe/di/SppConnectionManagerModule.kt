package ua.com.radiokot.strobe.di

import dagger.Module
import dagger.Provides
import ua.com.radiokot.strobe.control.logic.SppConnectionManager
import ua.com.radiokot.strobe.control.logic.SppConnectionProvider
import javax.inject.Singleton

@Module
class SppConnectionManagerModule(
        private val manager: SppConnectionManager
) {
    @Provides
    @Singleton
    fun get(): SppConnectionManager {
        return manager
    }

    @Provides
    @Singleton
    fun getProvider(): SppConnectionProvider {
        return manager
    }
}