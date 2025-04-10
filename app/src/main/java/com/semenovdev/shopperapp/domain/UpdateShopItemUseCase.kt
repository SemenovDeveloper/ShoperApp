package com.semenovdev.shopper.domain

class UpdateShopItemUseCase (private val shopListRepository: ShopListRepository) {
    fun updateShopItem (shopItem: ShopItem) {
        shopListRepository.updateShopItem(shopItem)
    }
}