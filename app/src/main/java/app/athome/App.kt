package app.athome

import android.app.Application
import app.athome.di.components.DaggerAppComponent

class App: Application() {

    val appComponent = DaggerAppComponent.builder().application(this).build()

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
    }

}