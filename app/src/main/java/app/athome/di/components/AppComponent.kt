package app.athome.di.components

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import app.athome.App
import app.athome.di.modules.AppModule
import app.athome.di.modules.ViewModelModule
import app.athome.repo.AppRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun appRepository(): AppRepository

    fun sharedPreferences(): SharedPreferences

    fun viewModelFactory(): ViewModelProvider.Factory

}