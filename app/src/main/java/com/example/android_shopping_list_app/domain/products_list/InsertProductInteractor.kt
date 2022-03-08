package com.example.android_shopping_list_app.domain.products_list

import com.example.android_shopping_list_app.data.repositories.products_list.ProductsListRepository
import com.example.android_shopping_list_app.entity.product.NewProductItem
import javax.inject.Inject

const val DEFAULT_PRODUCT_ACTIVE_STATE = true

class InsertProductInteractor @Inject constructor(
    private val repository: ProductsListRepository,
) {
    suspend operator fun invoke(productOwnerId: Long, name: String, amount: String) {
        val newProduct = NewProductItem(productOwnerId, name, amount, DEFAULT_PRODUCT_ACTIVE_STATE)
        repository.insert(newProduct)
    }
}