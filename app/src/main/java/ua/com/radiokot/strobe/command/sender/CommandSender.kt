package ua.com.radiokot.strobe.command.sender

import io.reactivex.Completable
import ua.com.radiokot.strobe.command.Command

interface CommandSender {
    fun send(command: Command): Completable
}