package com.example.android_shopping_list_app.domain.shopping_lists

import com.example.android_shopping_list_app.data.repositories.shopping_lists.ShoppingListsRepository
import com.example.android_shopping_list_app.entity.shopping_list.NewShoppingList
import javax.inject.Inject

class InsertListInteractor @Inject constructor(
    private val repository: ShoppingListsRepository,
) {
    suspend operator fun invoke(newListName: String) =
        repository.insert(NewShoppingList(newListName))
}