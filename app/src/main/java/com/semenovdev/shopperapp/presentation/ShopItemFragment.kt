package com.semenovdev.shopperapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.semenovdev.shopperapp.R
import com.semenovdev.shopperapp.domain.ShopItem


class ShopItemFragment() : Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSubmit: Button

    private var screenMode: String = arguments?.getString(MODE) ?: MODE_UNKNOWN
    private var shopItemID: Int = arguments?.getInt(SHOP_ITEM_ID) ?: ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        initView(view)
        setScreenMode()
        setTextInputListeners()
        setViewModelObservers()
    }

    private fun setViewModelObservers () {
        viewModel.isActionCompleted.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tilName.error = getString(R.string.error_shop_item_name)
            } else {
                tilName.error = null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tilCount.error = getString(R.string.error_shop_item_count)
            } else {
                tilCount.error = null
            }
        }
    }


    private fun parseParams  () {
        val args = requireArguments()
        if (!args.containsKey(MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Screen param mode $mode is incorrect")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemID = args.getInt(SHOP_ITEM_ID)
        }
    }

    private fun setScreenMode () {
        when(screenMode) {
            MODE_EDIT -> setupEditMode()
            MODE_ADD -> setupAddMode()
        }
    }

    private fun setupEditMode () {
        viewModel.getShopItem(shopItemID)

        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
            btnSubmit.setOnClickListener {
                viewModel.updateShopItemData(etName.text.toString(), etCount.text.toString())
            }
        }
    }

    private fun setupAddMode () {
        btnSubmit.setOnClickListener {
            viewModel.createShopItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun setTextInputListeners() {
        etName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        etCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun initView (view: View) {
        tilName = view.findViewById<TextInputLayout>(R.id.til_name)
        tilCount = view.findViewById<TextInputLayout>(R.id.til_count)
        etName = view.findViewById<EditText>(R.id.et_name)
        etCount = view.findViewById<EditText>(R.id.et_count)
        btnSubmit = view.findViewById<Button>(R.id.button_submit)
    }

    companion object {
        private const val MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemID: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemID)
                }
            }
        }
    }

}