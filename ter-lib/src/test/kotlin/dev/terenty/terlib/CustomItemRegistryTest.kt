package dev.terenty.terlib

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CustomItemRegistryTest {

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
    fun `tag and getId round-trip`() {
        val item = CustomItemRegistry.tag(ItemStack(Material.STONE), "heat_helmet")
        assertEquals("heat_helmet", CustomItemRegistry.getId(item))
    }

    @Test
    fun `getId returns null for untagged item`() {
        assertNull(CustomItemRegistry.getId(ItemStack(Material.STONE)))
    }

    @Test
    fun `hasId returns true for matching id`() {
        val item = CustomItemRegistry.tag(ItemStack(Material.STONE), "heat_helmet")
        assertTrue(CustomItemRegistry.hasId(item, "heat_helmet"))
    }

    @Test
    fun `hasId returns false for different id`() {
        val item = CustomItemRegistry.tag(ItemStack(Material.STONE), "heat_helmet")
        assertFalse(CustomItemRegistry.hasId(item, "night_bed"))
    }
}
