package net.the_hero_robot.arcane_kings.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.the_hero_robot.arcane_kings.impl.item.EvocationKatanaItem;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 */
public interface AKItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Redemption.MOD_ID);

    Item ARCHMAGE_BLADE = ITEMS.register("archmage_blade", EvocationKatanaItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.8F))
            .maxCount(1)
            .fireproof());

    static void init() {}
}
