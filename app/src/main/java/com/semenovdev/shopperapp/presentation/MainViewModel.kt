package com.semenovdev.shopper.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semenovdev.shopper.data.ShopListRepositoryImpl
import com.semenovdev.shopper.domain.DeleteShopItemUseCase
import com.semenovdev.shopper.domain.GetShopItemUseCase
import com.semenovdev.shopper.domain.GetShopListUseCase
import com.semenovdev.shopper.domain.ShopItem
import com.semenovdev.shopper.domain.UpdateShopItemUseCase

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repository)


    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteItem(shopItem)
    }

    fun getShopItem(shopItemId: Int): ShopItem {
        return getShopItemUseCase.getShopItemById(shopItemId)
    }

    fun updateShopItemEnabled(shopItem: ShopItem, enabled: Boolean) {
        var newShopItem = shopItem.copy(enabled = !shopItem.enabled)

        updateShopItemUseCase.updateShopItem(newShopItem)
    }
}