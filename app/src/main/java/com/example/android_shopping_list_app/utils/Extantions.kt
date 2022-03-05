package com.example.android_shopping_list_app.utils

import com.example.android_shopping_list_app.data.entity.ProductData
import com.example.android_shopping_list_app.data.entity.ShoppingListData
import com.example.android_shopping_list_app.entity.product.NewProductItem
import com.example.android_shopping_list_app.entity.shopping_list.NewShoppingList
import com.example.android_shopping_list_app.entity.product.ProductItem
import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList

fun ProductData.toProductItem() = ProductItem(id, name, amount, activeState)

fun ShoppingListData.toShoppingList() = ShoppingList(id, name)

fun NewShoppingList.toShoppingListData() = ShoppingListData(name = name)

fun NewProductItem.toProductData() = ProductData(
    productOwnerId = productOwnerId,
    name = name,
    amount = amount,
    activeState = activeState,
)