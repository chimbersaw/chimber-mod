package ru.chimchima.chimbermod

import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.ai.attributes.AttributeModifier.*
import net.minecraft.entity.ai.attributes.Attributes
import net.minecraft.entity.monster.piglin.PiglinBruteEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms.*
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.*
import org.apache.logging.log4j.LogManager
import ru.chimchima.chimbermod.items.ModItems
import ru.chimchima.chimbermod.loot.PiglinBarteringModifier
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import java.util.*


@Mod(ChimberMod.MOD_ID)
@EventBusSubscriber(modid = ChimberMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
object ChimberMod {
    const val MOD_ID = "chimber-mod"
    private val logger = LogManager.getLogger()

    init {
        ModItems.register(MOD_BUS)
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

    @SubscribeEvent
    fun on(event: RegistryEvent.Register<GlobalLootModifierSerializer<*>>) {
        event.registry.registerAll(
            PiglinBarteringModifier.Serializer().setRegistryName(
                ResourceLocation(MOD_ID, "modify_piglin_bartering")
            )
        )
    }
}
