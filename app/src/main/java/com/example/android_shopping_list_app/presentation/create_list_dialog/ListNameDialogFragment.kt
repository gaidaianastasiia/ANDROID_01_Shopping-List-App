package com.example.android_shopping_list_app.presentation.create_list_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.android_shopping_list_app.R
import com.example.android_shopping_list_app.databinding.DialogListNameBinding

private const val ID_ARGUMENTS_KEY = "ID_ARGUMENTS_KEY"
private const val NAME_ARGUMENTS_KEY = "NAME_ARGUMENTS_KEY"
private const val NO_LISTENER_EXCEPTION_MESSAGE = "must implement NoticeDialogListener"
private const val NULL_ACTIVITY_EXCEPTION_MESSAGE = "Activity cannot be null"

class ListNameDialogFragment : DialogFragment() {

    interface CreateListDialogListener {
        fun onListNameDialogPositiveClick(listName: String, listId: Long?)
    }

    lateinit var dialogListener: CreateListDialogListener
    lateinit var binding: DialogListNameBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            dialogListener = context as CreateListDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${activity.toString()} $NO_LISTENER_EXCEPTION_MESSAGE")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater: LayoutInflater? = activity?.layoutInflater

            if (inflater != null) {
                binding = DialogListNameBinding.inflate(inflater)
            }

            builder.setView(binding.root)
                .setCancelable(true)
                .setPositiveButton(R.string.save_button) { dialog, _ ->
                    val listName = binding.listNameEditText.text.toString()
                    dialogListener.onListNameDialogPositiveClick(listName, getListId())
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel_button) { dialog, _ ->
                    dialog.cancel()
                }

            binding.run {
                listNameEditText.requestFocus()

                if (isEditableState()) {
                    setEditableState()
                } else {
                    listNameDialogTitleTextView.text = getString(R.string.shopping_lists_create_dialog_title)
                }
            }

            builder.create()
        } ?: throw IllegalStateException(NULL_ACTIVITY_EXCEPTION_MESSAGE)
    }

    private fun getListId() = arguments?.getLong(ID_ARGUMENTS_KEY)

    private fun getPresetListName() = arguments?.getString(NAME_ARGUMENTS_KEY)

    private fun isEditableState() = getListId() != null

    private fun setEditableState() {
        val presetListName = getPresetListName()

        binding.run {
            listNameDialogTitleTextView.text = getString(R.string.shopping_lists_edit_dialog_title)
            listNameEditText.run {
                setText(presetListName)
                val cursorIndex = text.length
                setSelection(cursorIndex)
            }
        }
    }

    companion object {
        fun getInstance(listId: Long, listName: String) = ListNameDialogFragment()
            .apply {
                val bundle = Bundle()
                bundle.putLong(ID_ARGUMENTS_KEY, listId)
                bundle.putString(NAME_ARGUMENTS_KEY, listName)
                arguments = bundle
            }
    }
}