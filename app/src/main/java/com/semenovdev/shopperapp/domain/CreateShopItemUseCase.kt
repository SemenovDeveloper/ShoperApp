package com.semenovdev.shopper.domain

class CreateShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun createShopItem(shopItem: ShopItem) {
        shopListRepository.createShopItem(shopItem)
    }
}