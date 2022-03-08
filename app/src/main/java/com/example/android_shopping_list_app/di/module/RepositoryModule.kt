package com.example.android_shopping_list_app.di.module

import com.example.android_shopping_list_app.data.repositories.products_list.LocalProductsListRepository
import com.example.android_shopping_list_app.data.repositories.products_list.ProductsListRepository
import com.example.android_shopping_list_app.data.repositories.shopping_lists.LocalShoppingListsRepository
import com.example.android_shopping_list_app.data.repositories.shopping_lists.ShoppingListsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindShoppingListsRepository(repository: LocalShoppingListsRepository): ShoppingListsRepository

    @Binds
    @Singleton
    fun bindProductsListRepository(repository: LocalProductsListRepository): ProductsListRepository
}