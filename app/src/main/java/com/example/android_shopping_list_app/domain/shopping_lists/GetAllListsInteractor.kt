package com.example.android_shopping_list_app.domain.shopping_lists

import com.example.android_shopping_list_app.data.repositories.shopping_lists.ShoppingListsRepository
import javax.inject.Inject

class GetAllListsInteractor @Inject constructor(
    private val repository: ShoppingListsRepository
){
    suspend operator fun invoke() = repository.getAll()
}