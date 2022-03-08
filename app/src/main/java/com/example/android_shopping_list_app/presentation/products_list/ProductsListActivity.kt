package com.example.android_shopping_list_app.presentation.products_list

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_shopping_list_app.App
import com.example.android_shopping_list_app.R
import com.example.android_shopping_list_app.databinding.ActivityProductsListBinding
import com.example.android_shopping_list_app.entity.product.ProductItem
import com.example.android_shopping_list_app.presentation.controls_dialog.ControlsFragment
import com.example.android_shopping_list_app.presentation.shopping_lists.ShoppingListsActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val LIST_ID_INTENT_KEY = "list_id"
private const val INPUT_SEPARATOR = ":"
private const val DEFAULT_LIST_ID_VALUE = 0L
private const val PRODUCTS_LIST_CONTROLS_FRAGMENT_TAG = "ProductsListControlsFragment"

class ProductsListActivity :
    AppCompatActivity(),
    ProductsListContract.View,
    ProductsListAdapterListener,
    ControlsFragment.ShoppingListsControlsFragmentListener {

    @Inject
    lateinit var presenter: ProductsListPresenter
    private lateinit var binding: ActivityProductsListBinding
    private val activeProductsListAdapter = ActiveProductsListAdapter(this)
    private val inactiveProductsListAdapter = InactiveProductsListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        (application as App)
            .daggerAppComponent
            .inject(this)

        setAdapters()
        setClickListeners()
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
        val listId = intent.getLongExtra(LIST_ID_INTENT_KEY, DEFAULT_LIST_ID_VALUE)
        presenter.onViewStart(listId)
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }

    override fun onProductsListItemClick(id: Long, productActiveState: Boolean) {
        presenter.onChangeProductStateClick(id, productActiveState)
    }

    override fun onProductsListItemLongClick(id: Long) {
        presenter.onControlsProductButtonClick(id)
    }


    override fun onEditButtonClick(id: Long?) {
        id?.let { presenter.onEditProductButtonClick(it) }
    }

    override fun onDeleteButtonClick(id: Long?) {
        id?.let { presenter.onDeleteProductButtonClick(it) }
    }

    override fun setProductsListTitle(name: String) {
        binding.listTitleTextView.text = name
    }

    override fun showProductsList(
        activeProductsList: List<ProductItem>,
        inactiveProductsList: List<ProductItem>,
    ) {
        activeProductsListAdapter.submitList(activeProductsList)
        inactiveProductsListAdapter.submitList(inactiveProductsList)
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

    override fun showAddNewProductButton() {
        binding.addNewProductButton.visibility = View.VISIBLE
    }

    override fun hideAddNewProductButton() {
        binding.addNewProductButton.visibility = View.GONE
    }

    override fun showProductInput() {
        binding.productInputLayout.visibility = View.VISIBLE
        binding.productInputEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    override fun hideProductInput() {
        binding.productInputLayout.visibility = View.GONE
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    override fun setInputSeparator(inputValue: String) {
        binding.productInputEditText.setText(inputValue)
    }

    override fun setCursorToInputEnd() {
        val productInput = binding.productInputEditText
        val cursorIndex = productInput.text.length
        productInput.setSelection(cursorIndex)
    }

    override fun clearProductInput() {
        binding.productInputEditText.text.clear()
    }

    override fun setProductEditableData(data: String) {
        binding.productInputEditText.setText(data)
    }

    override fun showProductControls(id: Long) {
        val productControlsDialog = ControlsFragment.getInstance(id)
        val manager = supportFragmentManager
        productControlsDialog.show(manager, PRODUCTS_LIST_CONTROLS_FRAGMENT_TAG)
    }

    override fun showRecoverDeletedProductMessage(message: String, productId: Long) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    if (event == DISMISS_EVENT_TIMEOUT) {
                        presenter.onRecoverDeletedProductMessageDismissed(productId)
                    }
                }
            })
            .setAction(R.string.recover_button) { presenter.onRemovedProductRecovered() }
            .show()
    }

    private fun setAdapters() {
        val activeProductsRecyclerView = binding.activeProductsRecyclerView
        activeProductsRecyclerView.layoutManager = LinearLayoutManager(this)
        activeProductsRecyclerView.adapter = activeProductsListAdapter

        val inactiveProductsRecyclerView = binding.inactiveProductsRecyclerView
        inactiveProductsRecyclerView.layoutManager = LinearLayoutManager(this)
        inactiveProductsRecyclerView.adapter = inactiveProductsListAdapter
    }

    private fun setClickListeners() {
        binding.productInputEditText.addTextChangedListener(textWatcher)

        binding.addProductAmountButton.setOnClickListener {
            val productInputValue = binding.productInputEditText.text.toString()
            presenter.onAddProductAmountButtonClick(productInputValue)
        }

        binding.addNewProductButton.setOnClickListener {
            presenter.onAddNewProductButtonClick()
        }

        binding.saveProductButton.setOnClickListener {
            val productInputValue = binding.productInputEditText.text.toString()
            presenter.onSaveProductButtonClick(productInputValue)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                binding.addProductAmountButton.isEnabled = !it.contains(INPUT_SEPARATOR)
            }
        }
    }

    companion object {
        fun getIntent(
            context: ShoppingListsActivity,
            productsListId: Long,
        ) =
            Intent(context, ProductsListActivity::class.java).apply {
                putExtra(LIST_ID_INTENT_KEY, productsListId)
            }
    }
}