package ru.chimchima.chimbermod

import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.ai.attributes.AttributeModifier.*
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.monster.piglin.PiglinBruteEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.*
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import ru.chimchima.chimbermod.items.ModItems
import java.util.*

const val MOD_ID = "chimbermod"
private val logger = LogManager.getLogger()

@Mod(MOD_ID)
class ChimberMod {
    init {
        val eventBus = FMLJavaModLoadingContext.get().modEventBus
        ModItems.register(eventBus)
        MinecraftForge.EVENT_BUS.register(this)
    }

    private val halveHP = AttributeModifier(
        UUID.fromString("7b2ff858-5022-4e56-afee-6b6613abb599"),
        "Nerf max health",
        -0.5,
        Operation.MULTIPLY_TOTAL
    )

    private val halveDamage = AttributeModifier(
        UUID.fromString("a47044af-d828-41ab-89de-bdb26e1e15ae"),
        "Nerf damage",
        -0.5,
        Operation.MULTIPLY_TOTAL
    )

    @SubscribeEvent
    fun onEntityJoinWorldEvent(event: EntityJoinWorldEvent) {
        val brute = event.entity as? PiglinBruteEntity ?: return
        logger.info("Nerfing piglin brute hp and damage...")

        val hp = brute.getAttribute(Attributes.MAX_HEALTH)!!
        if (!hp.hasModifier(halveHP)) {
            hp.applyPersistentModifier(halveHP)
        }

        val dmg = brute.getAttribute(Attributes.ATTACK_DAMAGE)!!
        if (!dmg.hasModifier(halveDamage)) {
            dmg.applyPersistentModifier(halveDamage)
        }
    }
}
