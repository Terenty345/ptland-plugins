package dev.terenty.terlib

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ShapedRecipe
import java.io.StringReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RecipeLoaderTest {

    private lateinit var server: ServerMock

    @BeforeTest
    fun setUp() {
        server = MockBukkit.mock()
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun `loadFromConfig registers shaped recipe`() {
        val plugin = MockBukkit.createMockPlugin()
        val loader = RecipeLoader(plugin)

        val config = YamlConfiguration.loadConfiguration(StringReader("""
            diamond_helmet:
              result: DIAMOND_HELMET
              shape:
                - "DDD"
                - "D D"
              ingredients:
                D: DIAMOND
        """.trimIndent()))

        loader.loadFromConfig(config)

        val key = NamespacedKey(plugin, "diamond_helmet")
        val recipe = server.getRecipe(key)
        assertNotNull(recipe)
        assertEquals(Material.DIAMOND_HELMET, (recipe as ShapedRecipe).result.type)
    }

    @Test
    fun `loadFromConfig skips entry with unknown result material`() {
        val plugin = MockBukkit.createMockPlugin()
        val loader = RecipeLoader(plugin)

        val config = YamlConfiguration.loadConfiguration(StringReader("""
            bad_item:
              result: NOT_A_REAL_MATERIAL
              shape:
                - "XXX"
              ingredients:
                X: STONE
        """.trimIndent()))

        // Should not throw
        loader.loadFromConfig(config)

        val key = NamespacedKey(plugin, "bad_item")
        assertEquals(null, server.getRecipe(key))
    }
}
