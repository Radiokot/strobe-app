package ua.com.radiokot.strobe.control.logic

import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream

class SppConnection(
        private val socket: BluetoothSocket
) {
    val isActive: Boolean
        get() = socket.isConnected

    val outputStream: OutputStream
        get() = socket.outputStream

    val inputStream: InputStream
        get() = socket.inputStream

    fun disconnect() {
        outputStream.close()
        inputStream.close()
        socket.close()
    }
}