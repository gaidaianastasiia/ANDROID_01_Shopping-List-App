package com.example.android_shopping_list_app.di.module

import android.content.Context
import androidx.room.Room
import com.example.android_shopping_list_app.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Provides
    @Singleton
    fun provideDataBase(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()

    @Provides
    @Singleton
    fun provideShoppingListDao(database: AppDatabase) = database.shoppingListDao()

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase) = database.productDao()
}