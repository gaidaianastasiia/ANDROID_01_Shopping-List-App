package com.example.android_shopping_list_app.presentation.shopping_lists

import com.example.android_shopping_list_app.domain.shopping_lists.*
import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList
import com.example.android_shopping_list_app.presentation.base.BasePresenter
import kotlinx.coroutines.*
import javax.inject.Inject

class ShoppingListsPresenter @Inject constructor(
    private val getAllLists: GetAllListsInteractor,
    private val getListById: GetListByIdInteractor,
    private val insertList: InsertListInteractor,
    private val updateList: UpdateListInteractor,
    private val deleteList: DeleteListInteractor,
) : BasePresenter<ShoppingListsContract.View>(),
    ShoppingListsContract.Presenter {

    override fun onViewStart() {
        launch(Dispatchers.IO) {
            fetchShoppingLists()
        }
    }

    override fun onCreateListButtonClick() {
        view?.showCreateListDialog()
    }

    override fun onSaveNewListButtonClick(newListName: String) {
        view?.showLoader()

        launch(Dispatchers.IO) {
            insertList(newListName)
            fetchShoppingLists()
        }
    }

    override fun onSaveChangedListNameButtonClick(listId: Long, changedListName: String) {
        view?.showLoader()

        launch(Dispatchers.IO) {
            updateList(listId, changedListName)
            fetchShoppingLists()
        }
    }

    override fun onControlsListButtonClick(listId: Long) {
        view?.showControlsListDialog(listId)
    }

    override fun onEditListNameButtonClick(listId: Long) {
        launch(Dispatchers.IO) {
            val list = getListById(listId)
            view?.showEditListDialog(list)
        }
    }

    override fun onDeleteListButtonClick(listId: Long) {
        view?.showLoader()

        launch(Dispatchers.IO) {
            deleteProductFromUI(listId)
            val recoverDeletedListMessage = getRecoverDeletedListMessage(listId)

            launch(Dispatchers.Main) {
                view?.showRecoverDeletedListMessage(recoverDeletedListMessage, listId)
            }
        }
    }

    override fun onRecoverDeletedListMessageDismissed(listId: Long) {
        launch(Dispatchers.IO) {
            deleteList(listId)
        }
    }

    override fun onRemovedListRecovered() {
        launch(Dispatchers.IO) {
            fetchShoppingLists()
        }
    }

    private suspend fun fetchShoppingLists() {
        launch(Dispatchers.Main) {
            view?.showLoader()
        }

        val list = getAllLists()
        updateShoppingLists(list)
    }

    private fun updateShoppingLists(list: List<ShoppingList>) {
        launch(Dispatchers.Main) {
            if (list.isEmpty()) {
                view?.showEmptyState()
            } else {
                view?.hideEmptyState()
            }

            view?.hideLoader()
            view?.showShoppingLists(list)
        }
    }

    private suspend fun deleteProductFromUI(listId: Long) {
        val shoppingLists = getAllLists().toMutableList()
        shoppingLists.removeIf { it.id == listId }
        updateShoppingLists(shoppingLists)
    }

    private suspend fun getRecoverDeletedListMessage(listId: Long): String {
        val list = getListById(listId)
        val listName = list.name
        return "$listName $RECOVER_DELETED_LIST_MESSAGE"
    }

    companion object {
        private const val RECOVER_DELETED_LIST_MESSAGE = "has been removed"
    }
}