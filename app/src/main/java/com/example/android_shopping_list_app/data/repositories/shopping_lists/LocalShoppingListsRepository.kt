package com.example.android_shopping_list_app.data.repositories.shopping_lists

import com.example.android_shopping_list_app.data.dao.ShoppingListDao
import com.example.android_shopping_list_app.entity.shopping_list.NewShoppingList
import com.example.android_shopping_list_app.utils.toShoppingList
import com.example.android_shopping_list_app.utils.toShoppingListData
import javax.inject.Inject

class LocalShoppingListsRepository @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
) : ShoppingListsRepository {
    override suspend fun getAll() =
        shoppingListDao.getAll().map { it.toShoppingList() }

    override suspend fun getById(id: Long) =
        shoppingListDao.getById(id).toShoppingList()

    override suspend fun insert(newShoppingList: NewShoppingList) {
        shoppingListDao.insert(newShoppingList.toShoppingListData())
    }

    override suspend fun update(id: Long, name: String) {
        shoppingListDao.update(id, name)
    }

    override suspend fun delete(id: Long) {
        shoppingListDao.delete(id)
    }
}