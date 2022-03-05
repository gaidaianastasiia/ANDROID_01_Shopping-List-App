package com.example.android_shopping_list_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_shopping_list_app.data.dao.ProductDao
import com.example.android_shopping_list_app.data.dao.ShoppingListDao
import com.example.android_shopping_list_app.data.entity.ProductData
import com.example.android_shopping_list_app.data.entity.ShoppingListData

@Database(entities = [ShoppingListData::class, ProductData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun productDao(): ProductDao
}