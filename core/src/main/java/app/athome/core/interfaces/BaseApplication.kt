package app.athome.core.interfaces

import android.app.Application
import app.athome.core.di.DaggerCoreComponent

abstract class BaseApplication: Application() {

    private val coreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .application(this)
            .build()
    }

    abstract fun getClientId(): String

    fun getCoreProvider() = coreComponent as CoreProvider
}
