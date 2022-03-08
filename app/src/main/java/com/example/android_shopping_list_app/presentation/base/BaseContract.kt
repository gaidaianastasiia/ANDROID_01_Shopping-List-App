package com.example.android_shopping_list_app.presentation.base

interface BaseContract {
    interface View
    interface Presenter<V: View> {
        fun bind(view: V)
        fun unbind()
    }
}