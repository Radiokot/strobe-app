package ua.com.radiokot.strobe.devices.view.adapter

import android.bluetooth.BluetoothDevice
import android.view.ViewGroup
import org.jetbrains.anko.layoutInflater
import ua.com.radiokot.strobe.view.adapter.base.BaseRecyclerAdapter

class BluetoothDevicesAdapter: BaseRecyclerAdapter<BluetoothDevice, BluetoothDeviceViewHolder>() {
    override fun createItemViewHolder(parent: ViewGroup): BluetoothDeviceViewHolder {
        val view = parent.context.layoutInflater.inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false
        )

        return BluetoothDeviceViewHolder(view)
    }
}