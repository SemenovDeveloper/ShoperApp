package com.semenovdev.shopperapp.domain

class UpdateShopItemUseCase (private val shopListRepository: ShopListRepository) {
    fun updateShopItem (shopItem: ShopItem) {
        shopListRepository.updateShopItem(shopItem)
    }
}