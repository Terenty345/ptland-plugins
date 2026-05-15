# Task 4: ConfigUtils

**Files:**
- Create: `ter-lib/src/test/kotlin/dev/terenty/terlib/ConfigUtilsTest.kt`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/ConfigUtils.kt`

`ConfigUtils` — extension-функции на `ConfigurationSection` (интерфейс Paper для YAML-конфигов).
Тест не требует MockBukkit: `YamlConfiguration` работает без запущенного сервера.

---

- [ ] **Step 1: Написать тест**

```kotlin
// ter-lib/src/test/kotlin/dev/terenty/terlib/ConfigUtilsTest.kt
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
```

- [ ] **Step 2: Убедиться что тест падает**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.ConfigUtilsTest"
```

Expected: `FAILED` — `Unresolved reference: getDoubleOrDefault`

- [ ] **Step 3: Написать реализацию**

```kotlin
// ter-lib/src/main/kotlin/dev/terenty/terlib/ConfigUtils.kt
package dev.terenty.terlib

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getDoubleOrDefault(path: String, default: Double): Double =
    if (isSet(path)) getDouble(path) else default

fun ConfigurationSection.getStringOrDefault(path: String, default: String): String =
    getString(path) ?: default

fun ConfigurationSection.getIntOrDefault(path: String, default: Int): Int =
    if (isSet(path)) getInt(path) else default
```

- [ ] **Step 4: Убедиться что тест проходит**

```bash
./gradlew :ter-lib:test --tests "dev.terenty.terlib.ConfigUtilsTest"
```

Expected: `BUILD SUCCESSFUL`, все 6 тестов зелёные.

- [ ] **Step 5: Коммит**

```bash
git add ter-lib/src/
git commit -m "feat(ter-lib): add ConfigUtils extension functions"
```
