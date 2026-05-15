package dev.terenty.terlib

import org.bukkit.plugin.java.JavaPlugin

class TerLibPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: TerLibPlugin
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("TerLib ${pluginMeta.version} enabled")
    }
}
