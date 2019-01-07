package ua.com.radiokot.strobe.command

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ContinuousFlashingBpmCommand(bpm: Short): Command {
    private val bytes = ByteBuffer
            .allocate(3)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(4)
            .putShort(bpm)
            .array()

    override fun getBytes(): ByteArray = bytes
}