package com.example.android_shopping_list_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.android_shopping_list_app.data.entity.ProductData

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductData WHERE productOwnerId = :id")
    suspend fun getAll(id: Long): List<ProductData>

    @Query("SELECT * FROM ProductData WHERE id = :id")
    suspend fun getById(id: Long): ProductData

    @Insert
    suspend fun insert(productData: ProductData)

    @Query("UPDATE ProductData SET name = :changedName, amount = :changedAmount WHERE id = :id")
    suspend fun update(id: Long, changedName: String, changedAmount: String)

    @Query("UPDATE ProductData SET activeState = :changedActiveState WHERE id = :id")
    suspend fun updateProductActiveState(id: Long, changedActiveState: Boolean)

    @Query("DELETE FROM ProductData WHERE id = :id")
    suspend fun delete(id: Long)
}