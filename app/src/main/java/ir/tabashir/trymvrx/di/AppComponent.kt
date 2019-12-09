package ir.tabashir.trymvrx.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ir.tabashir.trymvrx.ui.MainActivity
import ir.tabashir.trymvrx.core.MvRxApplication
import ir.tabashir.trymvrx.ui.main.MainFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AssistedInjectModule::class,
        ApplicationModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: MvRxApplication)
    fun inject(activity: MainActivity)
    fun inject(activity: MainFragment)

}