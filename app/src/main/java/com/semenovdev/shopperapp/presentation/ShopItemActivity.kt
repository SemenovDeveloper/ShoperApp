package com.semenovdev.shopperapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.semenovdev.shopperapp.R
import com.semenovdev.shopperapp.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
//    private lateinit var viewModel: ShopItemViewModel
//
//    private lateinit var tilName: TextInputLayout
//    private lateinit var tilCount: TextInputLayout
//    private lateinit var etName: EditText
//    private lateinit var etCount: EditText
//    private lateinit var btnSubmit: Button
//
    private var screenMode: String = MODE_UNKNOWN
    private var shopItemID: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_layout)
        parseIntent()
//        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
//        initView()
        setScreenMode()
//        setTextInputListeners()
//        setViewModelObservers()
    }

//    private fun setViewModelObservers () {
//        viewModel.isActionCompleted.observe(this) {
//            finish()
//        }
//
//        viewModel.errorInputName.observe(this) {
//            if (it) {
//                tilName.error = getString(R.string.error_shop_item_name)
//            } else {
//                tilName.error = null
//            }
//        }
//
//        viewModel.errorInputCount.observe(this) {
//            if (it) {
//                tilCount.error = getString(R.string.error_shop_item_count)
//            } else {
//                tilCount.error = null
//            }
//        }
//    }
//
//
    private fun parseIntent () {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Screen param mode $mode is incorrect")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    private fun setScreenMode () {
        val fragment = when(screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemID)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode")
        }

        supportFragmentManager.beginTransaction().add(R.id.shop_item_container, fragment).commit()
    }

//    private fun setupEditMode () {
//        val shopItemID = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
//        viewModel.getShopItem(shopItemID)
//
//        viewModel.shopItem.observe(this) {
//            etName.setText(it.name)
//            etCount.setText(it.count.toString())
//            btnSubmit.setOnClickListener {
//                viewModel.updateShopItemData(etName.text.toString(), etCount.text.toString())
//            }
//        }
//    }
//
//    private fun setupAddMode () {
//        btnSubmit.setOnClickListener {
//            viewModel.createShopItem(etName.text.toString(), etCount.text.toString())
//        }
//    }
//
//    private fun setTextInputListeners() {
//        etName.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(
//                s: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {}
//
//            override fun onTextChanged(
//                s: CharSequence?,
//                start: Int,
//                before: Int,
//                count: Int
//            ) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//
//        etCount.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(
//                s: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {}
//
//            override fun onTextChanged(
//                s: CharSequence?,
//                start: Int,
//                before: Int,
//                count: Int
//            ) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }
//
//
//    private fun initView () {
//        tilName = findViewById<TextInputLayout>(R.id.til_name)
//        tilCount = findViewById<TextInputLayout>(R.id.til_count)
//        etName = findViewById<EditText>(R.id.et_name)
//        etCount = findViewById<EditText>(R.id.et_count)
//        btnSubmit = findViewById<Button>(R.id.button_submit)
//    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemID: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemID)
            return intent
        }
    }
}