package io.github.Cherryh4ck.toms3AprilFools

import io.github.Cherryh4ck.toms3AprilFools.Commands.OPSword
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Toms3AprilFools : JavaPlugin() {
    val playerDataPath = File(dataFolder, "playerdata")

    override fun onEnable() {
        if (!playerDataPath.exists()) {
            logger.info("Playerdata creado.")
            playerDataPath.mkdirs()
        }
        getCommand("32k")?.setExecutor(OPSword(this))

        logger.info("Happy april fools!")
        logger.info("Plugin inicializado.")
        popbobHack()
        witherSounds()
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun popbobHack(){
        val world = Bukkit.getWorld("world")
        val loc = Location(world, 0.0, 0.0, 0.0)
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            world?.strikeLightning(loc)
            Bukkit.getOnlinePlayers().forEach { player ->
                player.playSound(player.location, org.bukkit.Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1000f, 1f)
            }
        }, 0L, 10L)
    }

    fun witherSounds(){
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.playSound(player.location, org.bukkit.Sound.ENTITY_WITHER_SPAWN, 1000f, 1f)
            }
        }, 0L, 30L)
    }
}
