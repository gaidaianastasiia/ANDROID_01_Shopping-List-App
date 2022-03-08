package com.example.android_shopping_list_app.presentation.products_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_shopping_list_app.databinding.ActiveItemProductsListBinding
import com.example.android_shopping_list_app.entity.product.ProductItem

private val DIFF_CALLBACK: DiffUtil.ItemCallback<ProductItem> =
    object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem == newItem
        }
    }

class ActiveProductsListAdapter(
    private val productsListAdapterListener: ProductsListAdapterListener
) : ListAdapter<ProductItem, ActiveProductsListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ActiveItemProductsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        val productId = product.id
        val productActiveState = product.activeState

        holder.apply {
            bind(product)

            itemBinding.root.setOnClickListener {
                productsListAdapterListener.onProductsListItemClick(productId, productActiveState)
            }

            itemBinding.root.setOnLongClickListener {
                productsListAdapterListener.onProductsListItemLongClick(productId)
                true
            }
        }
    }

    class ViewHolder(
        val itemBinding: ActiveItemProductsListBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(product: ProductItem) {
            itemBinding.activeProductNameTextView.text = product.name
            itemBinding.activeProductAmountTextView.text = product.amount
        }
    }
}