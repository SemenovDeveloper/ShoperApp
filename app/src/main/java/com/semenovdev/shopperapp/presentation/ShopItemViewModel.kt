package com.semenovdev.shopperapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semenovdev.shopperapp.data.ShopListRepositoryImpl
import com.semenovdev.shopperapp.domain.CreateShopItemUseCase
import com.semenovdev.shopperapp.domain.GetShopItemUseCase
import com.semenovdev.shopperapp.domain.ShopItem
import com.semenovdev.shopperapp.domain.UpdateShopItemUseCase

class ShopItemViewModel (
    application: Application
): AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() {
            return _errorInputName
        }

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() {
            return _errorInputCount
        }

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() {
            return _shopItem
        }

    // if in LD needs only action, set value to Unit type
    private val _isActionCompleted = MutableLiveData<Unit>()
    val isActionCompleted: LiveData<Unit>
        get() {
            return _isActionCompleted
        }

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)
    private val createShopItemUseCase = CreateShopItemUseCase(repository)

    fun updateShopItemData(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputsValid = validateInput(name, count)
        if (isInputsValid) {
            _shopItem.value?.let {
                var newShopItem = it.copy(name, count)
                updateShopItemUseCase.updateShopItem(newShopItem)
                finishAction()
            }
        }

    }

    fun createShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isInputsValid = validateInput(name, count)
        if (isInputsValid) {
            val shopItem = ShopItem(name, count, true)
            createShopItemUseCase.createShopItem(shopItem)
            finishAction()
        }
    }

    fun getShopItem(shopItemId: Int) {
        _shopItem.value = getShopItemUseCase.getShopItemById(shopItemId)
    }

    private fun parseName (name: String?): String {

        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {

        return count?.toIntOrNull() ?: 0
    }

    private fun validateInput (name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName () {
        _errorInputName.value = false
    }

    fun resetErrorInputCount () {
        _errorInputCount.value = false
    }

    private fun finishAction () {
        _isActionCompleted.value = Unit
    }
}