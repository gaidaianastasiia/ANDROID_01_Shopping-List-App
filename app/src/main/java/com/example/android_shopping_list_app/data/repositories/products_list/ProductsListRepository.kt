package com.example.android_shopping_list_app.data.repositories.products_list

import com.example.android_shopping_list_app.entity.product.NewProductItem
import com.example.android_shopping_list_app.entity.product.ProductItem

interface ProductsListRepository {
    suspend fun getAll(id: Long): List<ProductItem>
    suspend fun getById(id: Long): ProductItem
    suspend fun insert(newProductItem: NewProductItem)
    suspend fun update(id: Long, name: String, amount: String)
    suspend fun updateActiveState(id: Long, activeState: Boolean)
    suspend fun delete(id: Long)
}