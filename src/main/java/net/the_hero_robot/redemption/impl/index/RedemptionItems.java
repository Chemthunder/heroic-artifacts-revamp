package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.item.CourtGlassItem;
import net.the_hero_robot.redemption.impl.item.DragonSwordItem;
import net.the_hero_robot.redemption.impl.item.HuntersCourtGlassItem;
import net.the_hero_robot.redemption.impl.item.KatanaItem;
import net.the_hero_robot.redemption.impl.util.KatanaType;
import net.the_hero_robot.redemption.impl.util.RedemptionItemSettings;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RedemptionItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Redemption.MOD_ID);

    Item QUARTZ_KATANA = ITEMS.register("quartz_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.QUARTZ, Items.QUARTZ)
            .colors(0xFFb8a591)
            .sweepParticle(0xFFc1c1d2, 0xFF535373)
            .hitSound(SoundEvents.BLOCK_NETHERRACK_BREAK)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item REDSTONE_KATANA = ITEMS.register("redstone_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.REDSTONE, Items.REDSTONE)
            .colors(0xFF6a1b28)
            .sweepParticle(0xFFe95050, 0xFF63374a)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item AMETHYST_KATANA = ITEMS.register("amethyst_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.AMETHYST, Items.AMETHYST_SHARD)
            .colors(0xFF603e78)
            .sweepParticle(0xFFffffff, 0xFFc1c2c2)
            .hitSound(SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item COPPER_KATANA = ITEMS.register("copper_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.COPPER, Items.COPPER_INGOT)
            .colors(0xFFca6d4c)
            .sweepParticle(0xFFea8770, 0xFF904931)
            .hitSound(SoundEvents.BLOCK_COPPER_BULB_BREAK)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item LAPIS_KATANA = ITEMS.register("lapis_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.LAPIS, Items.LAPIS_LAZULI)
            .colors(0xFF2d4377)
            .sweepParticle(0xFFc9edf9, 0xFF8bcadd)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item NETHERITE_KATANA = ITEMS.register("netherite_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.NETHERITE, Items.NETHERITE_SCRAP)
            .colors(0xFF5c4a5a)
            .sweepParticle(0xFF1a1a1a, 0xFF0e0e0e)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item EMERALD_KATANA = ITEMS.register("emerald_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.EMERALD, Items.EMERALD)
            .colors(0xFF328d3a)
            .sweepParticle(0xFF20d64b, 0xFF096a31)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));

    Item SCULK_KATANA = ITEMS.register("sculk_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.SCULK, Items.ECHO_SHARD)
            .colors(0xFF22423f)
            .sweepParticle(0xFF111b21, 0xFF034150)
            .hitSound(SoundEvents.BLOCK_SCULK_PLACE)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));


    // Ashiro
    Item ASHIRO_KATANA = ITEMS.register("ashiro_katana", KatanaItem::new, new RedemptionItemSettings()
            .katana(KatanaComponent.BladeType.SHEATHED, KatanaType.ASHIRO)
            .colors(0xFF3d3b3e, 0xFF4b1621, 0xFFb07b21, 0xFF190c0f)
            .maxCount(1)
            .attributeModifiers(KatanaItem.createAttributeModifiers(KatanaComponent.BladeType.SHEATHED)));


    // Misc
    Item COURT_GLASS = ITEMS.register("court_glass", CourtGlassItem::new, new RedemptionItemSettings()
            .maxCount(1));

    Item HUNTERS_GLASS = ITEMS.register("hunters_glass", HuntersCourtGlassItem::new, new RedemptionItemSettings()
            .maxCount(1));

    Item ICE_CREAM = ITEMS.register("ice_cream", Item::new, new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.35F).build()));

    Item DRAGON_SWORD = ITEMS.register("dragon_sword", DragonSwordItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.8F))
            .maxCount(1)
            .fireproof());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(RedemptionItems::addFoodEntries);

        // Colors
        modifyItemNameColor(HUNTERS_GLASS, 0xFFb73aaa);
        modifyItemNameColor(COURT_GLASS, 0xFF517bb2);
    }

    private static void addFoodEntries(FabricItemGroupEntries entries) {
        entries.addAfter(Items.COOKIE, ICE_CREAM);
    }
}
