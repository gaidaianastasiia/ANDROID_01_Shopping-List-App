package com.example.android_shopping_list_app.domain.shopping_lists

import com.example.android_shopping_list_app.data.repositories.shopping_lists.ShoppingListsRepository
import javax.inject.Inject

class UpdateListInteractor @Inject constructor(
    private val repository: ShoppingListsRepository
) {
    suspend operator fun invoke(id: Long, name: String) = repository.update(id, name)
}