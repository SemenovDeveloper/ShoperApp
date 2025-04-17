package com.semenovdev.shopperapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.semenovdev.shopperapp.domain.ShopItem
import com.semenovdev.shopperapp.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {
    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private var autoIncrementId = ShopItem.UNDEFINED_ID

    init {
        for (i in 0..10) {
            var item: ShopItem = ShopItem(name = "Name ${i.toString()}", enabled = Random.nextBoolean(), count = i)
            createShopItem(item)
        }
    }

    override fun createShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = ++autoIncrementId
        }
        shopList.add(shopItem)
        updateShopListLD()
    }

    override fun updateShopItem(shopItem: ShopItem) {
        val oldEl = getShopItem(shopItem.id)
        shopList.remove(oldEl)
        createShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopListLD()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with if $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateShopListLD() {
        shopListLD.value = shopList.toList()
    }

}