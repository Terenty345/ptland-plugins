# Task 3: ter-lib Scaffold

**Files:**
- Create: `ter-lib/build.gradle.kts`
- Create: `ter-lib/src/main/resources/plugin.yml`
- Create: `ter-lib/src/main/kotlin/dev/terenty/terlib/TerLibPlugin.kt`

---

- [ ] **Step 1: Создать `ter-lib/build.gradle.kts`**

```kotlin
val mockBukkitVersion: String by project

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:$mockBukkitVersion")
}
```

Объяснение зависимостей:
- `compileOnly("io.papermc.paper:paper-api:...")` уже добавлен в корневом `build.gradle.kts` для всех subprojects — повторять не нужно.
- MockBukkit транзитивно включает Paper API, поэтому в тестах Paper-классы доступны.

- [ ] **Step 2: Создать директории исходников**

```bash
mkdir -p ter-lib/src/main/kotlin/dev/terenty/terlib
mkdir -p ter-lib/src/main/resources
mkdir -p ter-lib/src/test/kotlin/dev/terenty/terlib
```

- [ ] **Step 3: Создать `ter-lib/src/main/resources/plugin.yml`**

Paper читает этот файл при загрузке плагина на сервер.

```yaml
name: TerLib
version: 1.0-SNAPSHOT
main: dev.terenty.terlib.TerLibPlugin
api-version: "1.21"
description: Shared utilities for PTLand plugins
```

- [ ] **Step 4: Создать `ter-lib/src/main/kotlin/dev/terenty/terlib/TerLibPlugin.kt`**

```kotlin
package dev.terenty.terlib

import org.bukkit.plugin.java.JavaPlugin

class TerLibPlugin : JavaPlugin() {

    companion object {
        lateinit var instance: TerLibPlugin
            private set
    }

    override fun onEnable() {
        instance = this
        logger.info("TerLib ${description.version} enabled")
    }
}
```

- [ ] **Step 5: Проверить компиляцию**

```bash
./gradlew :ter-lib:compileKotlin
```

Expected: `BUILD SUCCESSFUL`

- [ ] **Step 6: Коммит**

```bash
git add ter-lib/
git commit -m "feat(ter-lib): scaffold plugin module"
```
