package ua.com.radiokot.strobe.di

import dagger.Module
import dagger.Provides
import ua.com.radiokot.strobe.command.sender.CommandSender
import ua.com.radiokot.strobe.command.sender.CommandSenderFactory
import ua.com.radiokot.strobe.control.logic.SppConnectionProvider
import javax.inject.Singleton

@Module
class CommandSenderModule(
        private val factory: CommandSenderFactory
) {
    @Provides
    @Singleton
    fun get(connectionProvider: SppConnectionProvider): CommandSender {
        return factory.getSppCommandSender(connectionProvider)
    }
}