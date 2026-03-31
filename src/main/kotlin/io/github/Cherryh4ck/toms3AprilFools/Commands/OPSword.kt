package io.github.Cherryh4ck.toms3AprilFools.Commands

import io.github.Cherryh4ck.toms3AprilFools.Toms3AprilFools
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.io.File

class OPSword(private val plugin : Toms3AprilFools) : CommandExecutor {
    val minimessage = MiniMessage.miniMessage()
    val drunkness = PotionEffect(PotionEffectType.NAUSEA, 60 * 20, 5, false, true, true)
    val hunger = PotionEffect(PotionEffectType.HUNGER, 60 * 20, 5, false, true, true)
    val blindness = PotionEffect(PotionEffectType.BLINDNESS, 120 * 20, 0, false, true, true)
    val slowness = PotionEffect(PotionEffectType.SLOWNESS, 30 * 20, 3, false, true, true)
    val miningFatigue = PotionEffect(PotionEffectType.MINING_FATIGUE, 240 * 20, 2, false, true, true)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player){
            plugin.logger.warning("No se puede ejecutar este comando siendo la consola.")
            return true
        }

        val playerData = File(plugin.playerDataPath, "${sender.name}.yml")
        val locale = sender.locale().toString()
        if (playerData.exists()) {
            val message = if (locale.startsWith("es")){
                minimessage.deserialize("<red>No, no otra vez.")
            }
            else{
                minimessage.deserialize("<red>No, not again.")
            }
            sender.sendMessage(message)
            return true
        }
        playerData.createNewFile()
        val mainhandItem = sender.inventory.itemInMainHand
        val casco = sender.inventory.helmet

        if (mainhandItem.type != Material.AIR && mainhandItem.type.toString().contains("SWORD")) {
            val meta = mainhandItem.itemMeta
            meta.enchants.keys.forEach { enchantment ->
                meta.removeEnchant(enchantment)
            }
            mainhandItem.itemMeta = meta

            if (casco != null && casco.type != Material.AIR){
                sender.inventory.helmet = null
                sender.world.dropItemNaturally(sender.location, casco)
            }

            sender.inventory.helmet = createPumpkin()

            val message = if (locale.startsWith("es")){
                minimessage.deserialize("<light_purple>¡Lo siento!")
            }
            else{
                minimessage.deserialize("<light_purple>Sorry!")
            }
            val health = sender.health - 1.0
            sender.damage(health)
            sender.addPotionEffect(drunkness)
            sender.addPotionEffect(hunger)
            sender.addPotionEffect(blindness)
            sender.addPotionEffect(slowness)
            sender.addPotionEffect(miningFatigue)
            Bukkit.getOnlinePlayers().forEach { player ->
                player.playSound(player.location, org.bukkit.Sound.ITEM_TOTEM_USE, 1000f, 1f)
                player.playSound(player.location, org.bukkit.Sound.ENTITY_WITHER_SPAWN, 1000f, 1f)
            }


            sender.sendMessage(message)
        }
        else{
            val message = if (locale.startsWith("es")){
                minimessage.deserialize("<red>Debes sostener una espada en la mano.")
            }
            else{
                minimessage.deserialize("<red>You need to hold a sword in your hand to use this command.")
            }
            sender.sendMessage(message)
        }
        return true
    }

    fun createPumpkin(): ItemStack {
        val pumpkinTroll = ItemStack(Material.CARVED_PUMPKIN)
        val meta: ItemMeta? = pumpkinTroll.itemMeta
        val itemLore = listOf("<light_purple>Happy April Fools, newfag!")

        meta?.apply {
            setRarity(ItemRarity.EPIC)

            customName(minimessage.deserialize("I'm actually retarded >w<"))
            lore(itemLore.map{minimessage.deserialize(it)})
            addEnchant(Enchantment.BINDING_CURSE, 1, true)

        }
        pumpkinTroll.itemMeta = meta
        return pumpkinTroll
    }
}