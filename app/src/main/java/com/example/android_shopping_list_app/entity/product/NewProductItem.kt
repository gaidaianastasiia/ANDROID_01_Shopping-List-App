package com.example.android_shopping_list_app.entity.product

data class NewProductItem(
    val productOwnerId: Long,
    val name: String,
    val amount: String,
    val activeState: Boolean,
)
