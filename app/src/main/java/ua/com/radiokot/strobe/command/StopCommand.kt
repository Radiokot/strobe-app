package ua.com.radiokot.strobe.command

object StopCommand: Command {
    override fun getBytes(): ByteArray {
        return byteArrayOf(2)
    }
}