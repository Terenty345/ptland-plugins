# Task 5: MultiblockDetector

**Files:**
- Create: `ter-lib/src/test/kotlin/dev/terenty/terlib/MultiblockDetectorTest.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/BlockOffset.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/MultiblockDetector.kt`

`MultiblockDetector` принимает триггер-блок и паттерн (`Map<BlockOffset, Material>`), проверяет блоки вокруг.
`BlockOffset` — смещение (dx, dy, dz) относительно триггер-блока.

Тест использует MockBukkit для создания мирового окружения.

---

- [ ] **Step 1: Написать тест**

```kotlin
// ter-lib/src/test/kotlin/dev/terenty/terlib/MultiblockDetectorTest.kt
package dev.terenty.terlib

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.WorldMock
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
```

- [ ] **Step 2: Убедиться что тест падает**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.MultiblockDetectorTest"
```

Expected: `FAILED` — `Unresolved reference: BlockOffset`

- [ ] **Step 3: Написать `BlockOffset.kt`**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/BlockOffset.kt
package dev.terenty.terlib

data class BlockOffset(val dx: Int, val dy: Int, val dz: Int)
```

- [ ] **Step 4: Написать `MultiblockDetector.kt`**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/MultiblockDetector.kt
package dev.terenty.terlib

import org.bukkit.Material
import org.bukkit.block.Block

object MultiblockDetector {

    fun matches(trigger: Block, pattern: Map<BlockOffset, Material>): Boolean =
        pattern.all { (offset, expected) ->
            trigger.world.getBlockAt(
                trigger.x + offset.dx,
                trigger.y + offset.dy,
                trigger.z + offset.dz
            ).type == expected
        }
}
```

- [ ] **Step 5: Убедиться что тест проходит**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.MultiblockDetectorTest"
```

Expected: `BUILD SUCCESSFUL`, все 3 теста зелёные.

- [ ] **Step 6: Коммит**

```bash
git add ter-lib/src/
git commit -m "feat(ter-lib): add BlockOffset and MultiblockDetector"
```
