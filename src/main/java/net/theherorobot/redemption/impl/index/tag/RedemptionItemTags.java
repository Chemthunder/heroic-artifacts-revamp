package net.theherorobot.redemption.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.theherorobot.redemption.impl.Redemption;

public interface RedemptionItemTags {
    TagBuilder<Item> ITEMS = new TagBuilder<>(Redemption.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> KATANAS = ITEMS.register("katanas");
}
