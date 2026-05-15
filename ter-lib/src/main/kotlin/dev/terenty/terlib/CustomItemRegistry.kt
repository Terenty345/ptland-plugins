package dev.terenty.terlib

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object CustomItemRegistry {

    private val KEY = NamespacedKey("terlib", "item_id")

    fun tag(item: ItemStack, id: String): ItemStack {
        val meta = item.itemMeta ?: return item
        meta.persistentDataContainer.set(KEY, PersistentDataType.STRING, id)
        item.itemMeta = meta
        return item
    }

    fun getId(item: ItemStack): String? =
        item.itemMeta?.persistentDataContainer?.get(KEY, PersistentDataType.STRING)

    fun hasId(item: ItemStack, id: String): Boolean = getId(item) == id
}
