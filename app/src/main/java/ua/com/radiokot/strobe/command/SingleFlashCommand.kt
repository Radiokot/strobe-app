package ua.com.radiokot.strobe.command

class SingleFlashCommand:Command {
    override fun getBytes(): ByteArray {
        return byteArrayOf(1)
    }
}