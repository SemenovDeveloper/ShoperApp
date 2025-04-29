package com.semenovdev.shopperapp.domain

class CreateShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun createShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}