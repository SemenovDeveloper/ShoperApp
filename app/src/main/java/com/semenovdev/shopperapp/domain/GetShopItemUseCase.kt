package com.semenovdev.shopperapp.domain

class GetShopItemUseCase (private val shopListRepository: ShopListRepository) {
    suspend fun getShopItemById (id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}