package com.example.android_shopping_list_app

import android.app.Application
import com.example.android_shopping_list_app.di.component.AppComponent
import com.example.android_shopping_list_app.di.component.DaggerAppComponent

class App: Application() {
    lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerAppComponent = DaggerAppComponent
            .builder()
            .context(applicationContext)
            .build()
    }
}