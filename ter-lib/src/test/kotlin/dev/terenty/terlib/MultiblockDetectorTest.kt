package dev.terenty.terlib

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.world.WorldMock
import org.bukkit.Material
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MultiblockDetectorTest {

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
    fun `matches returns true when all blocks match pattern`() {
        world.getBlockAt(0, 64, 0).type = Material.LEVER
        world.getBlockAt(0, 63, 0).type = Material.NETHER_BRICKS

        val trigger = world.getBlockAt(0, 64, 0)
        val pattern = mapOf(
            BlockOffset(0, 0, 0) to Material.LEVER,
            BlockOffset(0, -1, 0) to Material.NETHER_BRICKS
        )

        assertTrue(MultiblockDetector.matches(trigger, pattern))
    }

    @Test
    fun `matches returns false when a block does not match`() {
        world.getBlockAt(0, 64, 0).type = Material.LEVER
        world.getBlockAt(0, 63, 0).type = Material.STONE

        val trigger = world.getBlockAt(0, 64, 0)
        val pattern = mapOf(
            BlockOffset(0, 0, 0) to Material.LEVER,
            BlockOffset(0, -1, 0) to Material.NETHER_BRICKS
        )

        assertFalse(MultiblockDetector.matches(trigger, pattern))
    }

    @Test
    fun `matches returns true for empty pattern`() {
        val trigger = world.getBlockAt(0, 64, 0)
        assertTrue(MultiblockDetector.matches(trigger, emptyMap()))
    }
}
