package ua.com.radiokot.strobe.command

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ContinuousFlashingHzCommand(hz: Byte) : Command {
    private val bytes = ByteBuffer
            .allocate(2)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(3)
            .put(hz)
            .array()

    override fun getBytes(): ByteArray = bytes
}