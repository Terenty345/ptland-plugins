package dev.terenty.terlib

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.Plugin

class RecipeLoader(private val plugin: Plugin) {

    fun loadFromConfig(config: ConfigurationSection) {
        config.getKeys(false).forEach { id ->
            val section = config.getConfigurationSection(id) ?: return@forEach
            val resultMaterial = Material.matchMaterial(
                section.getString("result", "") ?: ""
            ) ?: return@forEach
            val shape = section.getStringList("shape")
            val ingredients = section.getConfigurationSection("ingredients") ?: return@forEach

            val recipe = ShapedRecipe(NamespacedKey(plugin, id), ItemStack(resultMaterial))
            recipe.shape(*shape.toTypedArray())
            ingredients.getKeys(false).forEach { char ->
                val material = Material.matchMaterial(
                    ingredients.getString(char) ?: ""
                ) ?: return@forEach
                recipe.setIngredient(char[0], material)
            }
            plugin.server.addRecipe(recipe)
        }
    }
}
