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
