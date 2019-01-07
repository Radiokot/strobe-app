package ua.com.radiokot.strobe.command.sender

import ua.com.radiokot.strobe.control.logic.SppConnectionProvider

class CommandSenderFactory {
    fun getSppCommandSender(connectionProvider: SppConnectionProvider): CommandSender {
        return SppCommandSender(connectionProvider)
    }
}