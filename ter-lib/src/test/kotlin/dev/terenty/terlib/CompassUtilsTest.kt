package dev.terenty.terlib

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.world.WorldMock
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.CompassMeta
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CompassUtilsTest {

    private lateinit var server: ServerMock
    private lateinit var world: WorldMock

    @BeforeTest
    fun setUp() {
        server = MockBukkit.mock()
        world = server.addSimpleWorld("test")
    }

    @AfterTest
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun `pointAll sets lodestone on plain compass`() {
        val player = server.addPlayer()
        player.inventory.setItem(0, ItemStack(Material.COMPASS))

        val target = world.getBlockAt(100, 64, 200).location
        CompassUtils.pointAll(server, target)

        val meta = player.inventory.getItem(0)!!.itemMeta as CompassMeta
        assertEquals(target, meta.lodestone)
        assertFalse(meta.isLodestoneTracked)
    }

    @Test
    fun `pointAll does not change lodestone-tracked compass`() {
        val player = server.addPlayer()
        val compass = ItemStack(Material.COMPASS)
        val meta = compass.itemMeta as CompassMeta
        meta.isLodestoneTracked = true
        compass.itemMeta = meta
        player.inventory.setItem(0, compass)

        val target = world.getBlockAt(100, 64, 200).location
        CompassUtils.pointAll(server, target)

        val afterMeta = player.inventory.getItem(0)!!.itemMeta as CompassMeta
        assertTrue(afterMeta.isLodestoneTracked)
    }

    @Test
    fun `meteorImpact does not throw with online players`() {
        server.addPlayer()
        val location = world.getBlockAt(0, 64, 0).location
        ScreenEffects.meteorImpact(server, location)
    }
}
