package com.example.android_shopping_list_app.di.module

import com.example.android_shopping_list_app.domain.products_list.*
import com.example.android_shopping_list_app.domain.shopping_lists.*
import com.example.android_shopping_list_app.presentation.products_list.ProductsListContract
import com.example.android_shopping_list_app.presentation.products_list.ProductsListPresenter
import com.example.android_shopping_list_app.presentation.shopping_lists.ShoppingListsContract
import com.example.android_shopping_list_app.presentation.shopping_lists.ShoppingListsPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideShoppingListsPresenter(
        getAllListsInteractor: GetAllListsInteractor,
        getListByIdInteractor: GetListByIdInteractor,
        insertListInteractor: InsertListInteractor,
        updateListInteractor: UpdateListInteractor,
        deleteListInteractor: DeleteListInteractor
    ): ShoppingListsContract.Presenter =
        ShoppingListsPresenter(
            getAllListsInteractor,
            getListByIdInteractor,
            insertListInteractor,
            updateListInteractor,
            deleteListInteractor
        )

    @Provides
    fun provideProductsListPresenter(
        getAllProductsInteractor: GetAllProductsInteractor,
        getListByIdInteractor: GetListByIdInteractor,
        getProductByIdInteractor: GetProductByIdInteractor,
        insertProductInteractor: InsertProductInteractor,
        updateProductInteractor: UpdateProductInteractor,
        deleteProductInteractor: DeleteProductInteractor,
        updateProductActiveStateInteractor: UpdateProductActiveStateInteractor
    ): ProductsListContract.Presenter =
        ProductsListPresenter(
            getAllProductsInteractor,
            getListByIdInteractor,
            getProductByIdInteractor,
            insertProductInteractor,
            updateProductInteractor,
            deleteProductInteractor,
            updateProductActiveStateInteractor
        )
}