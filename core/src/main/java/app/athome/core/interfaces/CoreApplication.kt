package app.athome.core.interfaces

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.athome.core.di.DaggerCoreComponent

abstract class CoreApplication: Application() {

    private val coreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .application(this)
            .build()
    }

    fun getCoreProvider() = coreComponent as CoreProvider
}