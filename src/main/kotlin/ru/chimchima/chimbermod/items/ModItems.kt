package ru.chimchima.chimbermod.items

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.Rarity
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.chimchima.chimbermod.MOD_ID

object ModItems {
    private val items: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID)

    fun register(eventBus: IEventBus) {
        items.register("ruslan") {
            Item(Item.Properties().group(ItemGroup.MISC).rarity(Rarity.EPIC))
        }
        items.register(eventBus)
    }
}
