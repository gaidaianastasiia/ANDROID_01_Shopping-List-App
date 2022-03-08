package com.example.android_shopping_list_app.data.repositories.products_list

import com.example.android_shopping_list_app.data.dao.ProductDao
import com.example.android_shopping_list_app.entity.product.NewProductItem
import com.example.android_shopping_list_app.utils.toProductData
import com.example.android_shopping_list_app.utils.toProductItem
import javax.inject.Inject

class LocalProductsListRepository @Inject constructor(
    private val productDao: ProductDao,
) : ProductsListRepository {
    override suspend fun getAll(id: Long) = productDao.getAll(id).map {
        it.toProductItem()
    }

    override suspend fun getById(id: Long) = productDao.getById(id).toProductItem()

    override suspend fun insert(newProductItem: NewProductItem) {
        productDao.insert(newProductItem.toProductData())
    }

    override suspend fun update(id: Long, name: String, amount: String) {
        productDao.update(id, name, amount)
    }

    override suspend fun updateActiveState(id: Long, activeState: Boolean) {
        productDao.updateProductActiveState(id, !activeState)
    }

    override suspend fun delete(id: Long) {
        productDao.delete(id)
    }
}