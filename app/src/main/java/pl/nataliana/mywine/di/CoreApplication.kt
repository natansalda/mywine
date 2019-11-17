package pl.nataliana.mywine.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoreApplication)
            modules(listOf(
                dbModule,
                repositoryModule,
                uiModule
            ))
        }
    }
}