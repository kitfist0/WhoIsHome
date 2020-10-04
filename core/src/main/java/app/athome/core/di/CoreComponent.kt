package app.athome.core.di

import android.app.Application
import app.athome.core.interfaces.BaseApplication
import app.athome.core.interfaces.CoreProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent: CoreProvider {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): CoreComponent
    }

    fun inject(application: BaseApplication)
}
