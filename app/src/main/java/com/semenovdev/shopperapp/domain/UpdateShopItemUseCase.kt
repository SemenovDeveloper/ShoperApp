package com.semenovdev.shopperapp.domain

class UpdateShopItemUseCase (private val shopListRepository: ShopListRepository) {
    suspend fun updateShopItem (shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}