# Task 9: Build Verification

**Files:** нет новых файлов — только проверка.

---

- [ ] **Step 1: Запустить все тесты**

```bash
./gradlew :ter-lib:test
```

Expected:
```
ConfigUtilsTest > ... PASSED (6 tests)
MultiblockDetectorTest > ... PASSED (3 tests)
CustomItemRegistryTest > ... PASSED (4 tests)
RecipeLoaderTest > ... PASSED (2 tests)
CompassUtilsTest > ... PASSED (3 tests)
BUILD SUCCESSFUL
```

- [ ] **Step 2: Собрать все плагины**

```bash
./gradlew build
```

Expected: `BUILD SUCCESSFUL`

Проверить что JAR создан:
```bash
ls ter-lib/build/libs/
```

Expected: `ter-lib-1.0-SNAPSHOT.jar`

- [ ] **Step 3: Финальный коммит**

```bash
git status
```

Если есть незакоммиченные изменения — закоммитить. Если всё чисто:

```bash
git log --oneline -10
```

Expected — история коммитов должна содержать:
```
feat(ter-lib): add CompassUtils and ScreenEffects
feat(ter-lib): add RecipeLoader
feat(ter-lib): add CustomItemRegistry
feat(ter-lib): add BlockOffset and MultiblockDetector
feat(ter-lib): add ConfigUtils extension functions
feat(ter-lib): scaffold plugin module
build: set up Gradle Kotlin monorepo
docs: add CLAUDE.md project guide
```

---

**Plan 1 завершён.** Следующий шаг — Plan 2: `ter-climate`.
