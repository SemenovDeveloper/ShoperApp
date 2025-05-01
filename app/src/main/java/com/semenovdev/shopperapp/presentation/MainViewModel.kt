package com.semenovdev.shopperapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.semenovdev.shopperapp.data.ShopListRepositoryImpl
import com.semenovdev.shopperapp.domain.DeleteShopItemUseCase
import com.semenovdev.shopperapp.domain.GetShopListUseCase
import com.semenovdev.shopperapp.domain.ShopItem
import com.semenovdev.shopperapp.domain.UpdateShopItemUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
): AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteItem(shopItem)
        }
    }

    fun updateShopItemEnabled(shopItem: ShopItem) {
        viewModelScope.launch {
            var newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            updateShopItemUseCase.updateShopItem(newShopItem)
        }
    }
}