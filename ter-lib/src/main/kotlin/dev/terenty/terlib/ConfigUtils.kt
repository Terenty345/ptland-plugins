package dev.terenty.terlib

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getDoubleOrDefault(path: String, default: Double): Double =
    if (isSet(path)) getDouble(path) else default

fun ConfigurationSection.getStringOrDefault(path: String, default: String): String =
    getString(path) ?: default

fun ConfigurationSection.getIntOrDefault(path: String, default: Int): Int =
    if (isSet(path)) getInt(path) else default
