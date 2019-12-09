package ir.tabashir.trymvrx.core

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import ir.tabashir.trymvrx.di.AppComponent
import ir.tabashir.trymvrx.di.DaggerAppComponent
import timber.log.Timber

class MvRxApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "Timber $tag", message, t)
            }
        })
    }
}

val Activity.appComponent get() = (application as MvRxApplication).appComponent
val Fragment.appComponent get() = (context!!.applicationContext as MvRxApplication).appComponent

