# Task 7: RecipeLoader

**Files:**
- Create: `ter-lib/src/test/kotlin/dev/terenty/terlib/RecipeLoaderTest.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/RecipeLoader.kt`

`RecipeLoader` читает рецепты крафта из YAML-секции конфига и регистрирует их в Paper. Это позволяет менять рецепты без перекомпиляции плагина.

Формат конфига:
```yaml
heat_helmet:
  result: LEATHER_HELMET
  shape:
    - "GGG"
    - "G G"
  ingredients:
    G: GLASS
```

---

- [ ] **Step 1: Написать тест**

```kotlin
// ter-lib/src/test/kotlin/dev/terenty/terlib/RecipeLoaderTest.kt
package dev.terenty.terlib

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
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
```

- [ ] **Step 2: Убедиться что тест падает**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.RecipeLoaderTest"
```

Expected: `FAILED` — `Unresolved reference: RecipeLoader`

- [ ] **Step 3: Написать реализацию**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/RecipeLoader.kt
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
```

- [ ] **Step 4: Убедиться что тест проходит**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.RecipeLoaderTest"
```

Expected: `BUILD SUCCESSFUL`, оба теста зелёные.

- [ ] **Step 5: Коммит**

```bash
git add ter-lib/src/
git commit -m "feat(ter-lib): add RecipeLoader"
```
