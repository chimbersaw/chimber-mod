package ru.chimchima.chimbermod.items

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Rarity
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.chimchima.chimbermod.ChimberMod

object ModItems {
    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ChimberMod.MOD_ID)

    init {
        items.register("ruslan") {
            Item(Item.Properties().group(ItemGroup.MISC).rarity(Rarity.EPIC))
        }
    }

    fun register(eventBus: IEventBus) {
        items.register(eventBus)
    }
}
