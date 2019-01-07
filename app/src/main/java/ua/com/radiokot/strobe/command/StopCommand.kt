package ua.com.radiokot.strobe.command

class StopCommand: Command {
    override fun getBytes(): ByteArray {
        return byteArrayOf(2)
    }
}