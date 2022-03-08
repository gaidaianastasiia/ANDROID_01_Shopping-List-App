package com.example.android_shopping_list_app.presentation.shopping_lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_shopping_list_app.databinding.ItemShoppingListsBinding
import com.example.android_shopping_list_app.entity.shopping_list.ShoppingList

private val DIFF_CALLBACK: DiffUtil.ItemCallback<ShoppingList> =
    object : DiffUtil.ItemCallback<ShoppingList>() {
        override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
            return oldItem == newItem
        }
    }

class ShoppingListsAdapter(
    private val shoppingListsAdapterListener: ShoppingListsAdapterListener,
) : ListAdapter<ShoppingList, ShoppingListsAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface ShoppingListsAdapterListener {
        fun onListClick(listId: Long, listName: String)
        fun onListLongClick(listId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemShoppingListsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingList = getItem(position)

        holder.apply {
            bind(shoppingList)

            itemBinding.root.setOnClickListener {
                shoppingListsAdapterListener.onListClick(shoppingList.id, shoppingList.name)
            }

            itemBinding.root.setOnLongClickListener {
                shoppingListsAdapterListener.onListLongClick(shoppingList.id)
                true
            }
        }
    }

    class ViewHolder(
        val itemBinding: ItemShoppingListsBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(shoppingList: ShoppingList) {
            itemBinding.listItemNameTextView.text = shoppingList.name
        }
    }
}

