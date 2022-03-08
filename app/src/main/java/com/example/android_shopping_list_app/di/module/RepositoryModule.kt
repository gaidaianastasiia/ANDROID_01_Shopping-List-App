package com.example.android_shopping_list_app.di.module

import com.example.android_shopping_list_app.data.dao.ProductDao
import com.example.android_shopping_list_app.data.dao.ShoppingListDao
import com.example.android_shopping_list_app.data.repositories.products_list.LocalProductsListRepository
import com.example.android_shopping_list_app.data.repositories.products_list.ProductsListRepository
import com.example.android_shopping_list_app.data.repositories.shopping_lists.LocalShoppingListsRepository
import com.example.android_shopping_list_app.data.repositories.shopping_lists.ShoppingListsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideShoppingListsRepository(shoppingListDao: ShoppingListDao): ShoppingListsRepository
            = LocalShoppingListsRepository(shoppingListDao)

    @Provides
    @Singleton
    fun provideProductsListRepository(productDao: ProductDao): ProductsListRepository =
        LocalProductsListRepository(productDao)
}