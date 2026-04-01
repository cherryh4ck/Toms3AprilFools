package io.github.Cherryh4ck.toms3AprilFools.Listener

import io.github.Cherryh4ck.toms3AprilFools.Toms3AprilFools
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.inventory.ItemStack

class MobSpawnListener(private val plugin: Toms3AprilFools) : Listener {
    val minimessage = MiniMessage.miniMessage()
    @EventHandler

    fun onMobSpawn(event: CreatureSpawnEvent) {
        val reason = event.spawnReason
        if (reason == CreatureSpawnEvent.SpawnReason.DEFAULT || reason == CreatureSpawnEvent.SpawnReason.NATURAL) {
            val entity = event.entity
            if (entity.type == EntityType.ZOMBIE || entity.type == EntityType.SKELETON){
                equipMaxedOut(entity)
                val random = (0..100000).random()
                entity.customName(minimessage.deserialize("<green>Alien</green> <red>X${random}S</red>"))
            }
        }
    }

    private fun equipMaxedOut(entity: LivingEntity) {
        val equipment = entity.equipment ?: return

        val helmet = createMaxedItem(Material.DIAMOND_HELMET)
        val chestplate = createMaxedItem(Material.DIAMOND_CHESTPLATE)
        val leggings = createMaxedItem(Material.DIAMOND_LEGGINGS)
        val boots = createMaxedItem(Material.DIAMOND_BOOTS)
        equipment.helmet = helmet
        equipment.chestplate = chestplate
        equipment.leggings = leggings
        equipment.boots = boots

        if (entity.type == EntityType.SKELETON) {
            equipment.setItemInMainHand(createMaxedItem(Material.BOW))
        } else {
            equipment.setItemInMainHand(createMaxedItem(Material.DIAMOND_SWORD))
        }

        equipment.helmetDropChance = 0.4f
        equipment.chestplateDropChance = 0.4f
        equipment.leggingsDropChance = 0.4f
        equipment.bootsDropChance = 0.4f
    }

    private fun createMaxedItem(material: Material): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        if (!material.name.contains("SWORD") && !material.name.contains("BOW")) {
            meta?.addEnchant(Enchantment.PROTECTION, 4, true)
            meta?.addEnchant(Enchantment.UNBREAKING, 3, true)
            meta?.addEnchant(Enchantment.THORNS, 3, true)
        }
        else{
            when {
                material.name.contains("SWORD") -> {
                    meta?.addEnchant(Enchantment.SHARPNESS, 5, true)
                    meta?.addEnchant(Enchantment.FIRE_ASPECT, 2, true)
                    meta?.addEnchant(Enchantment.KNOCKBACK, 2, true)
                }
                material.name.contains("BOW") -> {
                    meta?.addEnchant(Enchantment.POWER, 5, true)
                    meta?.addEnchant(Enchantment.PUNCH, 2, true)
                    meta?.addEnchant(Enchantment.FLAME, 1, true)
                }
            }
        }

        item.itemMeta = meta
        return item
    }
}