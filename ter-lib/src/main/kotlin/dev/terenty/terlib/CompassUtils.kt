package dev.terenty.terlib

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.inventory.meta.CompassMeta

object CompassUtils {

    fun pointAll(server: Server, location: Location) {
        server.onlinePlayers.forEach { player ->
            player.inventory.contents.filterNotNull()
                .filter { it.type == Material.COMPASS }
                .forEach { compass ->
                    val meta = compass.itemMeta as? CompassMeta ?: return@forEach
                    if (meta.isLodestoneTracked) return@forEach
                    meta.lodestone = location
                    meta.isLodestoneTracked = false
                    compass.itemMeta = meta
                }
        }
    }
}
