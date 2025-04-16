package com.semenovdev.shopperapp.presentation

import androidx.lifecycle.ViewModel
import com.semenovdev.shopperapp.data.ShopListRepositoryImpl
import com.semenovdev.shopperapp.domain.DeleteShopItemUseCase
import com.semenovdev.shopperapp.domain.GetShopItemUseCase
import com.semenovdev.shopperapp.domain.GetShopListUseCase
import com.semenovdev.shopperapp.domain.ShopItem
import com.semenovdev.shopperapp.domain.UpdateShopItemUseCase

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

    fun updateShopItemEnabled(shopItem: ShopItem) {
        var newShopItem = shopItem.copy(enabled = !shopItem.enabled)

        updateShopItemUseCase.updateShopItem(newShopItem)
    }
}