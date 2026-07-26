package net.the_hero_robot.redemption.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 */
public interface RNItemTags {
    TagBuilder<Item> ITEMS = new TagBuilder<>(Redemption.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> KATANAS = ITEMS.register("katanas");
}
