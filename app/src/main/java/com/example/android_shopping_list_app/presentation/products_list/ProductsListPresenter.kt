package com.example.android_shopping_list_app.presentation.products_list

import com.example.android_shopping_list_app.domain.products_list.*
import com.example.android_shopping_list_app.domain.shopping_lists.GetListByIdInteractor
import com.example.android_shopping_list_app.entity.product.ProductItem
import com.example.android_shopping_list_app.presentation.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INPUT_SEPARATOR = ":"
private const val RECOVER_DELETED_PRODUCT_MESSAGE = "has been removed"

class ProductsListPresenter @Inject constructor(
    private val getAllProducts: GetAllProductsInteractor,
    private val getListById: GetListByIdInteractor,
    private val getProductById: GetProductByIdInteractor,
    private val insertProduct: InsertProductInteractor,
    private val updateProduct: UpdateProductInteractor,
    private val deleteProduct: DeleteProductInteractor,
    private val updateProductActiveState: UpdateProductActiveStateInteractor,
) : BasePresenter<ProductsListContract.View>(),
    ProductsListContract.Presenter {

    var listId: Long? = null
    private var updatableProductId: Long? = null
    private var isProductNew = true

    override fun onViewStart(productsListId: Long) {
        view?.showLoader()
        listId = productsListId

        launch(Dispatchers.IO) {
            listId?.let {
                val listName = getListById(it).name
                view?.setProductsListTitle(listName)
                fetchProductsList()
            }
        }
    }

    override fun onAddNewProductButtonClick() {
        view?.hideAddNewProductButton()
        view?.showProductInput()
    }

    override fun onSaveProductButtonClick(productInputValue: String) {
        listId?.let { productOwnerId ->
            val name = getProductName(productInputValue)
            val amount = getProductAmount(productInputValue)

            if (isProductNew) {
                insertNewProduct(productOwnerId, name, amount)
            } else {
                updatableProductId?.let { id ->
                    updateProductData(id, name, amount)
                }
            }

            updatableProductId = null
            isProductNew = true
            view?.clearProductInput()
        }
    }

    override fun onControlsProductButtonClick(id: Long) {
        view?.showProductControls(id)
    }

    override fun onEditProductButtonClick(productId: Long) {
        isProductNew = false
        view?.showLoader()

        launch {
            val product = getProductById(productId)
            val editableData = getProductEditableData(product)
            updatableProductId = productId

            launch(Dispatchers.Main) {
                view?.hideLoader()
                view?.setProductEditableData(editableData)
                view?.hideAddNewProductButton()
                view?.showProductInput()
            }
        }
    }

    override fun onAddProductAmountButtonClick(inputValue: String) {
        val isSeparator = inputValue.contains(INPUT_SEPARATOR)

        if (!isSeparator) {
            val newProductInputValue = "$inputValue $INPUT_SEPARATOR "
            view?.setInputSeparator(newProductInputValue)
            view?.setCursorToInputEnd()
        }
    }

    override fun onDeleteProductButtonClick(productId: Long) {
        view?.showLoader()

        launch(Dispatchers.IO) {
            deleteProductFromUI(productId)
            val recoverDeletedProductMessage = getRecoverDeletedProductMessage(productId)

            launch(Dispatchers.Main) {
                view?.showRecoverDeletedProductMessage(recoverDeletedProductMessage, productId)
            }
        }
    }

    override fun onRecoverDeletedProductMessageDismissed(id: Long) {
        launch(Dispatchers.IO) {
            deleteProduct(id)
        }
    }

    override fun onRemovedProductRecovered() {
        view?.showLoader()

        launch(Dispatchers.IO) {
            fetchProductsList()
        }
    }

    override fun onChangeProductStateClick(id: Long, activeState: Boolean) {
        view?.showLoader()

        launch(Dispatchers.IO) {
            updateProductActiveState(id, activeState)
            fetchProductsList()
        }
    }

    override fun bind(view: ProductsListContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    private suspend fun fetchProductsList() {
        launch(Dispatchers.Main) {
            view?.showLoader()
        }

        listId?.let {
            val list = getAllProducts(it)
            updateProductsList(list)
        }
    }

    private fun updateProductsList(productsList: List<ProductItem>) {
        launch(Dispatchers.Main) {
            val isListEmpty = productsList.isEmpty()
            updateListEmptyState(isListEmpty)

            val activeProductsList = productsList.filter { it.activeState }
            val inactiveProductsList = productsList.filter { !it.activeState }

            view?.hideLoader()
            view?.showProductsList(activeProductsList, inactiveProductsList)
        }
    }

    private fun updateListEmptyState(isListEmpty: Boolean) {
        if (isListEmpty) {
            view?.showEmptyState()
        } else {
            view?.hideEmptyState()
        }
    }

    private fun getProductName(inputValue: String): String {
        val indexOfSeparator = inputValue.indexOf(INPUT_SEPARATOR)
        val isSeparator = indexOfSeparator != -1
        var productName = inputValue

        if (isSeparator) {
            productName = inputValue.substring(0, indexOfSeparator)
        }

        return productName
    }

    private fun getProductAmount(inputValue: String): String {
        val indexOfSeparator = inputValue.indexOf(INPUT_SEPARATOR)
        val isSeparator = indexOfSeparator != -1
        var productAmount = ""

        if (isSeparator) {
            productAmount = inputValue.substring(indexOfSeparator + 2, inputValue.length)
        }

        return productAmount
    }

    private fun insertNewProduct(listId: Long, name: String, amount: String) {
        view?.showLoader()
        view?.hideProductInput()
        view?.showAddNewProductButton()

        launch(Dispatchers.IO) {
            insertProduct(listId, name, amount)
            fetchProductsList()
        }
    }

    private fun updateProductData(id: Long, name: String, amount: String) {
        view?.showLoader()
        view?.hideProductInput()
        view?.showAddNewProductButton()

        launch(Dispatchers.IO) {
            updateProduct(id, name, amount)
            fetchProductsList()
        }
    }

    private fun getProductEditableData(product: ProductItem): String {
        val name = product.name
        val amount = product.amount
        var editableData = name

        if (amount.isNotEmpty()) {
            editableData += "$INPUT_SEPARATOR $amount"
        }

        return editableData
    }

    private suspend fun getRecoverDeletedProductMessage(productId: Long): String {
        val product = getProductById(productId)
        val productName = product.name
        return "$productName $RECOVER_DELETED_PRODUCT_MESSAGE"
    }

    private suspend fun deleteProductFromUI(productId: Long) {
        listId?.let { id ->
            val productsList = getAllProducts(id).toMutableList()
            productsList.removeIf { it.id == productId }
            updateProductsList(productsList)
        }
    }
}