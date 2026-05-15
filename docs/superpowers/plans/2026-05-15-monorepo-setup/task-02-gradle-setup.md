# Task 2: Gradle Monorepo Setup

**Files:**
- Create: `gradle.properties`
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`
- Modify: `.gitignore`
- Create: `ter-climate/build.gradle.kts` (заглушка)
- Create: `ter-lore/build.gradle.kts` (заглушка)
- Create: `ter-structures/build.gradle.kts` (заглушка)
- Create: `ter-items/build.gradle.kts` (заглушка)

---

- [ ] **Step 1: Создать `gradle.properties`**

Центральное место для версий — меняешь здесь, все 5 плагинов подхватывают изменение.

```properties
paperVersion=1.21.1-R0.1-SNAPSHOT
mockBukkitVersion=4.22.0
```

> Если `mockBukkitVersion=4.22.0` не найдётся при сборке — проверь актуальную версию на
> https://central.sonatype.com/artifact/org.mockbukkit.mockbukkit/mockbukkit-v1.21/versions

- [ ] **Step 2: Создать `settings.gradle.kts`**

```kotlin
rootProject.name = "ptland-plugins"

include("ter-lib")
include("ter-climate")
include("ter-lore")
include("ter-structures")
include("ter-items")
```

- [ ] **Step 3: Создать корневой `build.gradle.kts`**

```kotlin
plugins {
    kotlin("jvm") version "2.0.21" apply false
}

val paperVersion: String by project

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "dev.terenty"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("io.papermc.paper:paper-api:$paperVersion")
    }

    kotlin {
        jvmToolchain(21)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
```

- [ ] **Step 4: Обновить `.gitignore`**

Добавить в конец файла:

```gitignore
# Gradle
.gradle/
build/

# IDE
.idea/
*.iml
```

- [ ] **Step 5: Создать заглушки для будущих плагинов**

Gradle требует, чтобы директории плагинов из `settings.gradle.kts` существовали. Создать пустые `build.gradle.kts` для каждого:

```bash
mkdir -p ter-climate ter-lore ter-structures ter-items
touch ter-climate/build.gradle.kts
touch ter-lore/build.gradle.kts
touch ter-structures/build.gradle.kts
touch ter-items/build.gradle.kts
```

- [ ] **Step 6: Инициализировать Gradle Wrapper**

```bash
gradle wrapper --gradle-version 8.10
```

Expected: создаст `gradlew`, `gradlew.bat`, `gradle/wrapper/`.

- [ ] **Step 7: Проверить что Gradle видит все проекты**

```bash
./gradlew projects
```

Expected:
```
Root project 'ptland-plugins'
+--- Project ':ter-climate'
+--- Project ':ter-items'
+--- Project ':ter-lib'
+--- Project ':ter-lore'
\--- Project ':ter-structures'
```

- [ ] **Step 8: Коммит**

```bash
git add gradle.properties settings.gradle.kts build.gradle.kts .gitignore \
        ter-climate/build.gradle.kts ter-lore/build.gradle.kts \
        ter-structures/build.gradle.kts ter-items/build.gradle.kts \
        gradlew gradlew.bat gradle/
git commit -m "build: set up Gradle Kotlin monorepo"
```
