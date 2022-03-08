package com.example.android_shopping_list_app.presentation.products_list

import com.example.android_shopping_list_app.entity.product.ProductItem
import com.example.android_shopping_list_app.presentation.base.BaseContract

interface ProductsListContract {
    interface View : BaseContract.View {
        fun setProductsListTitle(name: String)
        fun showProductsList(
            activeProductsList: List<ProductItem>,
            inactiveProductsList: List<ProductItem>,
        )
        fun showErrorMessage()
        fun showEmptyState()
        fun hideEmptyState()
        fun showLoader()
        fun hideLoader()
        fun showAddNewProductButton()
        fun hideAddNewProductButton()
        fun showProductInput()
        fun hideProductInput()
        fun setInputSeparator(inputValue: String)
        fun setCursorToInputEnd()
        fun clearProductInput()
        fun setProductEditableData(data: String)
        fun showProductControls(id: Long)
        fun showRecoverDeletedProductMessage(message: String, productId: Long)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onViewStart(productsListId: Long)
        fun onAddNewProductButtonClick()
        fun onSaveProductButtonClick(productInputValue: String)
        fun onControlsProductButtonClick(id: Long)
        fun onEditProductButtonClick(productId: Long)
        fun onAddProductAmountButtonClick(inputValue: String)
        fun onDeleteProductButtonClick(productId: Long)
        fun onRecoverDeletedProductMessageDismissed(id: Long)
        fun onRemovedProductRecovered()
        fun onChangeProductStateClick(id: Long, activeState: Boolean)
    }
}