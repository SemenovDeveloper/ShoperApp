package com.semenovdev.shopperapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.semenovdev.shopperapp.R

class ShopItemActivity : AppCompatActivity() {
    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_layout)
        viewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        val mode = intent.getStringExtra("extra_mode")
        initView()
    }

    private fun initView () {
        tilName = findViewById<TextInputLayout>(R.id.til_name)
        tilCount = findViewById<TextInputLayout>(R.id.til_count)
        etName = findViewById<EditText>(R.id.et_name)
        etCount = findViewById<EditText>(R.id.et_count)
        btnSubmit = findViewById<Button>(R.id.button_submit)
    }

    companion object {
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

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