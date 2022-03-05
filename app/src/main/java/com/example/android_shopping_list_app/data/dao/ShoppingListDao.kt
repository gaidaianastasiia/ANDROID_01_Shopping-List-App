package com.example.android_shopping_list_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_shopping_list_app.data.entity.ShoppingListData

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM ShoppingListData")
    suspend fun getAll(): List<ShoppingListData>

    @Query("SELECT * FROM ShoppingListData WHERE id = :id")
    suspend fun getById(id: Long): ShoppingListData

    @Insert
    suspend fun insert(shoppingListData: ShoppingListData)

    @Query("UPDATE ShoppingListData SET name = :changedListName WHERE id = :listId")
    suspend fun update(listId: Long, changedListName: String)

    @Query("DELETE FROM ShoppingListData WHERE id = :listId")
    suspend fun delete(listId: Long)
}