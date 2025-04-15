package com.semenovdev.shopperapp.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun createShopItem(shopItem: ShopItem)

    fun updateShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId: Int) : ShopItem

    fun getShopList() : LiveData<List<ShopItem>>
}