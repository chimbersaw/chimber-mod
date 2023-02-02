package ru.chimchima.chimbermod.loot

import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootContext
import net.minecraft.loot.conditions.ILootCondition
import net.minecraft.util.JSONUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.util.ResourceLocationException
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.registries.ForgeRegistries
import javax.annotation.Nonnull

class PiglinBarteringModifier(
    private val item: Item,
    conditionsIn: Array<ILootCondition>
) : LootModifier(conditionsIn) {
    @Nonnull
    override fun doApply(generatedLoot: MutableList<ItemStack>, context: LootContext): List<ItemStack> {
        if (context.random.nextDouble() <= 0.01) {
            generatedLoot.replaceAll {
                ItemStack(item, 1)
            }
        }
        return generatedLoot
    }

    class Serializer : GlobalLootModifierSerializer<PiglinBarteringModifier>() {
        override fun read(
            name: ResourceLocation,
            json: JsonObject,
            conditionsIn: Array<ILootCondition>
        ): PiglinBarteringModifier {
            val location = ResourceLocation(JSONUtils.getString(json, "item"))
            val item = ForgeRegistries.ITEMS.getValue(location)
                ?: throw ResourceLocationException("Invalid item location: $location")
            return PiglinBarteringModifier(item, conditionsIn)
        }

        override fun write(instance: PiglinBarteringModifier): JsonObject {
            val json = makeConditions(instance.conditions)
            json.addProperty("item", ForgeRegistries.ITEMS.getKey(instance.item).toString())
            return json
        }
    }
}
