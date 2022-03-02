package com.example.android_shopping_list_app

import com.example.android_shopping_list_app.di.component.AppComponent
import com.example.android_shopping_list_app.di.component.DaggerAppComponent

class Application: android.app.Application() {
    lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        daggerAppComponent = DaggerAppComponent
            .builder()
            .context(applicationContext)
            .build()
    }
}