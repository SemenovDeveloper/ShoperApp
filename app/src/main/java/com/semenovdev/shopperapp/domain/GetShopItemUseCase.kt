package com.semenovdev.shopperapp.domain

class GetShopItemUseCase (private val shopListRepository: ShopListRepository) {
    fun getShopItemById (id: Int): ShopItem {
        return shopListRepository.getShopItem(id)
    }
}