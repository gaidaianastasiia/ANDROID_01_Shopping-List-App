package com.example.android_shopping_list_app.domain.products_list

import com.example.android_shopping_list_app.data.repositories.products_list.ProductsListRepository
import javax.inject.Inject

class GetProductByIdInteractor @Inject constructor(
    private val repository: ProductsListRepository
) {
    suspend operator fun invoke(id: Long) = repository.getById(id)
}