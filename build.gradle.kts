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
        "compileOnly"("io.papermc.paper:paper-api:$paperVersion")
    }

    extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
