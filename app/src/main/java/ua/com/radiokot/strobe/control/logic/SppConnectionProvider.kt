package ua.com.radiokot.strobe.control.logic

import io.reactivex.Single

interface SppConnectionProvider {
    fun getConnection(): Single<SppConnection>
}