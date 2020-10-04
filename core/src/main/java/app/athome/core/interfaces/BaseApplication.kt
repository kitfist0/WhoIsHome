package app.athome.core.interfaces

import android.app.Application
import androidx.work.Configuration
import app.athome.core.di.DaggerCoreComponent
import javax.inject.Inject

abstract class BaseApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var workerConfiguration: Configuration

    private val coreComponent by lazy {
        DaggerCoreComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        coreComponent.inject(this)
    }

    abstract fun getClientId(): String

    fun getCoreProvider(): CoreProvider = coreComponent

    // Setup custom configuration for WorkManager with a DelegatingWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }
}
