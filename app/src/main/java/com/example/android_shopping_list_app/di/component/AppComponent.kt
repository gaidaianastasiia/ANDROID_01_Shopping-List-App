package com.example.android_shopping_list_app.di.component

import android.content.Context
import com.example.android_shopping_list_app.di.module.PresenterModule
import com.example.android_shopping_list_app.di.module.RepositoryModule
import com.example.android_shopping_list_app.di.module.StorageModule
import com.example.android_shopping_list_app.presentation.products_list.ProductsListActivity
import com.example.android_shopping_list_app.presentation.shopping_lists.ShoppingListsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        PresenterModule::class,
        RepositoryModule::class,
        StorageModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(activity: ShoppingListsActivity)
    fun inject(activity: ProductsListActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}