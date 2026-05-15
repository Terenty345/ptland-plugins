# Task 6: CustomItemRegistry

**Files:**
- Create: `ter-lib/src/test/kotlin/dev/terenty/terlib/CustomItemRegistryTest.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/CustomItemRegistry.kt`

`CustomItemRegistry` тегирует предметы строковым ID через `PersistentDataContainer` — хранилище внутри `ItemMeta`, которое сохраняется в NBT предмета и не теряется при перезапуске сервера.

---

- [ ] **Step 1: Написать тест**

```kotlin
// ter-lib/src/test/kotlin/dev/terenty/terlib/CustomItemRegistryTest.kt
package dev.terenty.terlib

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
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
```

- [ ] **Step 2: Убедиться что тест падает**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.CustomItemRegistryTest"
```

Expected: `FAILED` — `Unresolved reference: CustomItemRegistry`

- [ ] **Step 3: Написать реализацию**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/CustomItemRegistry.kt
package dev.terenty.terlib

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object CustomItemRegistry {

    private val KEY = NamespacedKey("terlib", "item_id")

    fun tag(item: ItemStack, id: String): ItemStack {
        val meta = item.itemMeta ?: return item
        meta.persistentDataContainer.set(KEY, PersistentDataType.STRING, id)
        item.itemMeta = meta
        return item
    }

    fun getId(item: ItemStack): String? =
        item.itemMeta?.persistentDataContainer?.get(KEY, PersistentDataType.STRING)

    fun hasId(item: ItemStack, id: String): Boolean = getId(item) == id
}
```

- [ ] **Step 4: Убедиться что тест проходит**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.CustomItemRegistryTest"
```

Expected: `BUILD SUCCESSFUL`, все 4 теста зелёные.

- [ ] **Step 5: Коммит**

```bash
git add ter-lib/src/
git commit -m "feat(ter-lib): add CustomItemRegistry"
```
