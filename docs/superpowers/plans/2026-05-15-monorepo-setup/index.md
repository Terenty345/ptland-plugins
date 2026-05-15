# Plan 1: Monorepo Setup + ter-lib

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Настроить Gradle Kotlin monorepo и реализовать все утилиты `ter-lib`, от которых зависят плагины сезона.

**Architecture:** Gradle multi-project build. `ter-lib` — отдельный Paper-плагин (загружается сервером), остальные плагины подключают его через `compileOnly(project(":ter-lib"))` и `depend: [TerLib]` в `plugin.yml`. Плагины общаются только через Paper Events.

**Tech Stack:** Kotlin 2.0.21, Gradle 8.x (Kotlin DSL), Paper API 1.21.11-R0.1-SNAPSHOT, JUnit 5 (через `kotlin("test")`), MockBukkit 4.22.0

**Scope:** Это Plan 1 из 5. Последующие планы — ter-climate, ter-structures, ter-items, ter-lore — зависят от этого плана.

---

## Карта файлов

```
CLAUDE.md
settings.gradle.kts
build.gradle.kts
gradle.properties
.gitignore                                           (изменить)
ter-lib/
├── build.gradle.kts
├── src/main/resources/plugin.yml
└── src/main/kotlin/dev/terenty/terlib/
│   ├── TerLibPlugin.kt
│   ├── ConfigUtils.kt
│   ├── BlockOffset.kt
│   ├── MultiblockDetector.kt
│   ├── CustomItemRegistry.kt
│   ├── RecipeLoader.kt
│   ├── CompassUtils.kt
│   └── ScreenEffects.kt
└── src/test/kotlin/dev/terenty/terlib/
    ├── ConfigUtilsTest.kt
    ├── MultiblockDetectorTest.kt
    ├── CustomItemRegistryTest.kt
    └── RecipeLoaderTest.kt
ter-climate/build.gradle.kts                         (заглушка)
ter-lore/build.gradle.kts                            (заглушка)
ter-structures/build.gradle.kts                      (заглушка)
ter-items/build.gradle.kts                           (заглушка)
```

---

## Задачи

- [ ] [Task 1: CLAUDE.md](task-01-claude-md.md)
- [ ] [Task 2: Gradle monorepo setup](task-02-gradle-setup.md)
- [ ] [Task 3: ter-lib scaffold](task-03-terlib-scaffold.md)
- [ ] [Task 4: ConfigUtils](task-04-config-utils.md)
- [ ] [Task 5: MultiblockDetector](task-05-multiblock-detector.md)
- [ ] [Task 6: CustomItemRegistry](task-06-custom-item-registry.md)
- [ ] [Task 7: RecipeLoader](task-07-recipe-loader.md)
- [ ] [Task 8: CompassUtils + ScreenEffects](task-08-compass-screen-effects.md)
- [ ] [Task 9: Build verification](task-09-build-verify.md)
