package com.example.android_shopping_list_app.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : BaseContract.View> :
    CoroutineScope,
    BaseContract.Presenter<V> {
    var view: V? = null
    private var coroutineJob: Job? = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + requireNotNull(coroutineJob)

    override fun bind(view: V) {
        this.view = view
        coroutineJob = Job()
    }

    override fun unbind() {
        this.view = null
        coroutineJob?.cancel()
        coroutineJob = null
    }
}