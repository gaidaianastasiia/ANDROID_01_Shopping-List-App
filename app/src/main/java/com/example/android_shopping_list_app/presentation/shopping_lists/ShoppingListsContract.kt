package com.example.android_shopping_list_app.presentation.shopping_lists

import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList
import com.example.android_shopping_list_app.presentation.base.BaseContract

interface ShoppingListsContract {
    interface View: BaseContract.View {
        fun showShoppingLists(list: List<ShoppingList>)
        fun showErrorMessage()
        fun showEmptyState()
        fun hideEmptyState()
        fun showLoader()
        fun hideLoader()
        fun showCreateListDialog()
        fun showControlsListDialog(listId: Long)
        fun showEditListDialog(list: ShoppingList)
        fun showRecoverDeletedListMessage(message: String, listId: Long)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun onViewStart()
        fun onCreateListButtonClick()
        fun onSaveNewListButtonClick(newListName: String)
        fun onSaveChangedListNameButtonClick(listId: Long, changedListName: String)
        fun onControlsListButtonClick(listId: Long)
        fun onEditListNameButtonClick(listId: Long)
        fun onDeleteListButtonClick(listId: Long)
        fun onRecoverDeletedListMessageDismissed(listId: Long)
        fun onRemovedListRecovered()
    }
}