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
