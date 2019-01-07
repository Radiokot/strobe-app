package ua.com.radiokot.strobe.command.sender

import io.reactivex.Completable
import ua.com.radiokot.strobe.command.Command
import ua.com.radiokot.strobe.control.logic.SppConnectionProvider

class SppCommandSender(
        private val connectionProvider: SppConnectionProvider
) : CommandSender {
    override fun send(command: Command): Completable {
        return connectionProvider
                .getConnection()
                .doOnSuccess { connection ->
                    if (!connection.isActive) {
                        throw  IllegalStateException("The connection is inactive")
                    }

                    connection.outputStream.write(command.getBytes())
                }
                .ignoreElement()
    }
}