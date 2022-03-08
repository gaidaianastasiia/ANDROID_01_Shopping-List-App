package com.example.android_shopping_list_app.presentation.controls_dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_shopping_list_app.databinding.FragmentControlsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val NO_LISTENER_EXCEPTION_MESSAGE =
    "must implement ShoppingListsControlsFragmentListener"
private const val ID_ARGUMENTS_KEY = "ID_ARGUMENTS_KEY"

class ControlsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentControlsBinding
    private lateinit var controlsListener: ShoppingListsControlsFragmentListener

    interface ShoppingListsControlsFragmentListener {
        fun onEditButtonClick(id: Long?)
        fun onDeleteButtonClick(id: Long?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            controlsListener = context as ShoppingListsControlsFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${activity.toString()} $NO_LISTENER_EXCEPTION_MESSAGE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentControlsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        setEditButtonClickListener()
        setDeleteButtonClickListener()
    }

    private fun setEditButtonClickListener() {
        binding.editListNameButton.setOnClickListener {
            val listId = getListId()
            controlsListener.onEditButtonClick(listId)
            dismissAllowingStateLoss()
        }
    }

    private fun setDeleteButtonClickListener() {
        binding.deleteListNameButton.setOnClickListener {
            val listId = getListId()
            controlsListener.onDeleteButtonClick(listId)
            dismissAllowingStateLoss()
        }
    }

    private fun getListId() = arguments?.getLong(ID_ARGUMENTS_KEY)

    companion object {
        fun getInstance(listId: Long) = ControlsFragment()
            .apply {
                val bundle = Bundle()
                bundle.putLong(ID_ARGUMENTS_KEY, listId)
                arguments = bundle
            }
    }
}