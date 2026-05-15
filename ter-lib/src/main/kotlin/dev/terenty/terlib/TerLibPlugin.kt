package dev.terenty.terlib

import org.bukkit.plugin.java.JavaPlugin

class TerLibPlugin : JavaPlugin() {

    companion object {
        var instance: TerLibPlugin? = null
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("TerLib ${pluginMeta.version} enabled")
    }

    override fun onDisable() {
        instance = null
    }
}
