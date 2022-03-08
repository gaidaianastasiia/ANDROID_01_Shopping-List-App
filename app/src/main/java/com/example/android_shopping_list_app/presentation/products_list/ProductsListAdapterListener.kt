package com.example.android_shopping_list_app.presentation.products_list

interface ProductsListAdapterListener {
    fun onProductsListItemClick(id: Long, productActiveState: Boolean)
    fun onProductsListItemLongClick(id: Long)
}