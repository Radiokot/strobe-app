package ua.com.radiokot.strobe.command

object SingleFlashCommand : Command {
    override fun getBytes(): ByteArray {
        return byteArrayOf(1)
    }
}