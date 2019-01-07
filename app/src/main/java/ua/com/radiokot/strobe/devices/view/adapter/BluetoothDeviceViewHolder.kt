package ua.com.radiokot.strobe.devices.view.adapter

import android.bluetooth.BluetoothDevice
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.view.adapter.base.BaseViewHolder

class BluetoothDeviceViewHolder(view: View) : BaseViewHolder<BluetoothDevice>(view) {
    private val nameTextView: TextView = view.find(android.R.id.text1)
    private val macTextView: TextView = view.find(android.R.id.text2)

    override fun bind(item: BluetoothDevice) {
        nameTextView.text = item.name
                ?: view.context.getString(R.string.unknown_device)
        macTextView.text = item.address
    }
}