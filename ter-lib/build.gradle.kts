val mockBukkitVersion: String by project

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:$mockBukkitVersion")
}
