package ua.com.radiokot.strobe.devices.view

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_devices.*
import ua.com.radiokot.strobe.R
import ua.com.radiokot.strobe.base.BaseActivity
import ua.com.radiokot.strobe.devices.view.adapter.BluetoothDevicesAdapter

class DevicesActivity : BaseActivity() {
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val listAdapter = BluetoothDevicesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)

        supportActionBar?.title = getString(R.string.devices_title)

        initSwipeRefresh()
        initList()

        ensureBluetoothIsEnabled()
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent))
        swipe_refresh.setOnRefreshListener {
            displayPairedDevices()
            swipe_refresh.post { swipe_refresh.isRefreshing = false }
        }
    }

    private fun initList() {
        devices_list.adapter = listAdapter
        devices_list.layoutManager = LinearLayoutManager(this)

        listAdapter.onItemClick { _, item ->
            onDeviceSelected(item)
        }
    }

    private fun ensureBluetoothIsEnabled() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST)
            return
        }

        onBluetoothEnabled()
    }

    private fun onBluetoothEnabled() {
        displayPairedDevices()
    }

    private fun displayPairedDevices() {
        listAdapter.setData(bluetoothAdapter.bondedDevices)
    }

    private fun onDeviceSelected(device: BluetoothDevice) {
        sppConnectionManager.setDevice(device)
        openControl()
    }

    private fun openControl() {
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ENABLE_BLUETOOTH_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                onBluetoothEnabled()
            } else {
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.devices, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.open_bt_settings -> openBluetoothSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openBluetoothSettings() {
        val intent = Intent()
        intent.action = android.provider.Settings.ACTION_BLUETOOTH_SETTINGS
        startActivity(intent)
    }

    companion object {
        private val ENABLE_BLUETOOTH_REQUEST = "enable_bt".hashCode() and 0xffff
    }
}