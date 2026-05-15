# Task 8: CompassUtils + ScreenEffects

**Files:**
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/CompassUtils.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/ScreenEffects.kt`

Обе утилиты работают с онлайн-игроками. Тест для `CompassUtils` проверяет что намагниченные компасы не изменяются. `ScreenEffects` тестируется только на отсутствие исключений — визуальные эффекты на клиенте в unit-тестах не видны.

---

- [ ] **Step 1: Написать тест**

```kotlin
// В конец файла ter-lib/src/test/kotlin/dev/terenty/terlib/CustomItemRegistryTest.kt
// (или создать отдельный файл CompassUtilsTest.kt)
```

Создать `ter-lib/src/test/kotlin/dev/terenty/terlib/CompassUtilsTest.kt`:

```kotlin
package dev.terenty.terlib

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.WorldMock
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
        // Просто проверяем что не падает с исключением
        ScreenEffects.meteorImpact(server, location)
    }
}
```

- [ ] **Step 2: Убедиться что тест падает**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.CompassUtilsTest"
```

Expected: `FAILED` — `Unresolved reference: CompassUtils`

- [ ] **Step 3: Написать `CompassUtils.kt`**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/CompassUtils.kt
package dev.terenty.terlib

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.inventory.meta.CompassMeta

object CompassUtils {

    fun pointAll(server: Server, location: Location) {
        server.onlinePlayers.forEach { player ->
            player.inventory.contents.filterNotNull()
                .filter { it.type == Material.COMPASS }
                .forEach { compass ->
                    val meta = compass.itemMeta as? CompassMeta ?: return@forEach
                    if (meta.isLodestoneTracked) return@forEach
                    meta.lodestone = location
                    meta.isLodestoneTracked = false
                    compass.itemMeta = meta
                }
        }
    }
}
```

- [ ] **Step 4: Написать `ScreenEffects.kt`**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/ScreenEffects.kt
package dev.terenty.terlib

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Server
import org.bukkit.Sound
import java.time.Duration

object ScreenEffects {

    fun meteorImpact(server: Server, location: Location) {
        val title = Title.title(
            Component.empty(),
            Component.text("Что-то упало с неба..."),
            Title.Times.times(
                Duration.ofMillis(500),
                Duration.ofSeconds(3),
                Duration.ofMillis(500)
            )
        )
        server.onlinePlayers.forEach { player ->
            player.playHurtAnimation(0f)
            player.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 0.5f)
            player.showTitle(title)
        }
        location.world?.spawnParticle(Particle.EXPLOSION_EMITTER, location, 5, 2.0, 2.0, 2.0, 0.1)
    }
}
```

- [ ] **Step 5: Убедиться что тест проходит**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.CompassUtilsTest"
```

Expected: `BUILD SUCCESSFUL`, все 3 теста зелёные.

- [ ] **Step 6: Коммит**

```bash
git add ter-lib/src/
git commit -m "feat(ter-lib): add CompassUtils and ScreenEffects"
```
