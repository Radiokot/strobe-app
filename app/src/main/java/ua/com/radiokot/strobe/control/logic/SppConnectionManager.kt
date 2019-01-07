package ua.com.radiokot.strobe.control.logic

import android.bluetooth.BluetoothDevice
import io.reactivex.Single
import io.reactivex.rxkotlin.toMaybe
import io.reactivex.rxkotlin.toSingle
import java.util.*

class SppConnectionManager {
    private var mConnection: SppConnection? = null
    private var mDevice: BluetoothDevice? = null

    val isConnected: Boolean
        get() = mConnection?.isActive == true

    /**
     * Sets device to connect.
     * If there is already a connection it will be disconnected
     *
     * @see connect
     */
    fun setDevice(device: BluetoothDevice) {
        disconnect()
        mDevice = device
    }

    /**
     * Connects to the set device.
     * If there is already a connection it will be disconnected
     *
     * @see setDevice
     */
    fun connect(): Single<SppConnection> {
        mConnection?.disconnect()

        return {
            val socket = mDevice?.createRfcommSocketToServiceRecord(SPP_SERVICE_UUID)
                    ?: throw IllegalStateException("No device set")
            socket.connect()

            val connection = SppConnection(socket)
            mConnection = connection
            connection
        }.toSingle()
    }

    /**
     * Instantly returns an active connection or performs connection if it was disconnected
     *
     * @see connect
     */
    fun getConnection(): Single<SppConnection> {
        return mConnection
                .toMaybe()
                .switchIfEmpty(connect())
    }

    fun disconnect() {
        mConnection?.disconnect()
    }

    companion object {
        private val SPP_SERVICE_UUID =
                UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }
}