package dev.terenty.terlib

import org.bukkit.configuration.file.YamlConfiguration
import java.io.StringReader
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfigUtilsTest {

    private fun cfg(yaml: String) = YamlConfiguration.loadConfiguration(StringReader(yaml))

    @Test
    fun `getDoubleOrDefault returns value when key exists`() {
        assertEquals(42.0, cfg("temp: 42.0").getDoubleOrDefault("temp", 0.0))
    }

    @Test
    fun `getDoubleOrDefault returns default when key missing`() {
        assertEquals(5.0, cfg("").getDoubleOrDefault("missing", 5.0))
    }

    @Test
    fun `getStringOrDefault returns value when key exists`() {
        assertEquals("hello", cfg("name: hello").getStringOrDefault("name", "fallback"))
    }

    @Test
    fun `getStringOrDefault returns default when key missing`() {
        assertEquals("fallback", cfg("").getStringOrDefault("missing", "fallback"))
    }

    @Test
    fun `getIntOrDefault returns value when key exists`() {
        assertEquals(10, cfg("count: 10").getIntOrDefault("count", 0))
    }

    @Test
    fun `getIntOrDefault returns default when key missing`() {
        assertEquals(3, cfg("").getIntOrDefault("missing", 3))
    }
}
