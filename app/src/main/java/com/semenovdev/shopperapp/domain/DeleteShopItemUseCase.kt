package com.semenovdev.shopperapp.domain

class DeleteShopItemUseCase (private val shopListRepository: ShopListRepository) {
    fun deleteItem (shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}