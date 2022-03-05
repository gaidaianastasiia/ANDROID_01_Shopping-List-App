package com.example.android_shopping_list_app.data.repositories.shopping_lists

import com.example.android_shopping_list_app.entity.shopping_list.NewShoppingList
import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList

interface ShoppingListsRepository {
    suspend fun getAll(): List<ShoppingList>
    suspend fun getById(id: Long): ShoppingList
    suspend fun insert(newShoppingList: NewShoppingList)
    suspend fun update(id: Long, name: String)
    suspend fun delete(id: Long)
}