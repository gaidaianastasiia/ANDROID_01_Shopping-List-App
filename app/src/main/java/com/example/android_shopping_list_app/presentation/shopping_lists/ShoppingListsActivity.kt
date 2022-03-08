package com.example.android_shopping_list_app.presentation.shopping_lists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_shopping_list_app.App
import com.example.android_shopping_list_app.R
import com.example.android_shopping_list_app.databinding.ActivityShoppingListsBinding
import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList
import com.example.android_shopping_list_app.presentation.controls_dialog.ControlsFragment
import com.example.android_shopping_list_app.presentation.create_list_dialog.ListNameDialogFragment
import com.example.android_shopping_list_app.presentation.products_list.ProductsListActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val CREATE_LIST_FRAGMENT_DIALOG_TAG = "CreateListDialog"
private const val SHOPPING_LIST_CONTROLS_FRAGMENT_TAG = "ShoppingListsControlsFragment"

class ShoppingListsActivity :
    AppCompatActivity(),
    ShoppingListsContract.View,
    ListNameDialogFragment.CreateListDialogListener,
    ControlsFragment.ShoppingListsControlsFragmentListener,
    ShoppingListsAdapter.ShoppingListsAdapterListener {

    @Inject
    lateinit var presenter: ShoppingListsPresenter
    private lateinit var binding: ActivityShoppingListsBinding
    private var adapter = ShoppingListsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingListsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        (application as App)
            .daggerAppComponent
            .inject(this)

        binding.listsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.listsRecyclerView.adapter = adapter
        binding.showCreateListDialogButton.setOnClickListener {
            presenter.onCreateListButtonClick()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
        presenter.onViewStart()
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    override fun showShoppingLists(list: List<ShoppingList>) {
        adapter.submitList(list)
    }

    override fun showErrorMessage() {
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun showEmptyState() {
        binding.emptyStateLayout.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        binding.emptyStateLayout.visibility = View.GONE
    }

    override fun showLoader() {
        binding.progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding.progressBarLayout.visibility = View.GONE
    }

    override fun showCreateListDialog() {
        val createListDialog = ListNameDialogFragment()
        val manager = supportFragmentManager
        createListDialog.show(manager, CREATE_LIST_FRAGMENT_DIALOG_TAG)
    }

    override fun showControlsListDialog(listId: Long) {
        val controlsListDialog = ControlsFragment.getInstance(listId)
        val manager = supportFragmentManager
        controlsListDialog.show(manager, SHOPPING_LIST_CONTROLS_FRAGMENT_TAG)
    }

    override fun showEditListDialog(list: ShoppingList) {
        val createListDialog = ListNameDialogFragment.getInstance(list.id, list.name)
        val manager = supportFragmentManager
        createListDialog.show(manager, CREATE_LIST_FRAGMENT_DIALOG_TAG)
    }

    override fun showRecoverDeletedListMessage(message: String, listId: Long) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if (event == DISMISS_EVENT_TIMEOUT) {
                        presenter.onRecoverDeletedListMessageDismissed(listId)
                    }
                }
            })
            .setAction(R.string.recover_button) { presenter.onRemovedListRecovered() }
            .show()
    }

    override fun onListNameDialogPositiveClick(listName: String, listId: Long?) {
        listId?.let { it ->
            presenter.onSaveChangedListNameButtonClick(it, listName)
        } ?: run {
            presenter.onSaveNewListButtonClick(listName)
        }
    }

    override fun onEditButtonClick(id: Long?) {
        id?.let { presenter.onEditListNameButtonClick(it) }
    }

    override fun onDeleteButtonClick(id: Long?) {
        id?.let { presenter.onDeleteListButtonClick(it) }
    }

    override fun onListClick(listId: Long, listName: String) {
        val intent = ProductsListActivity.getIntent(this, listId)
        startActivity(intent)
    }

    override fun onListLongClick(listId: Long) {
        listId.let { presenter.onControlsListButtonClick(it) }
    }
}