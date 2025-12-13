package silly.chemthunder.redemption.index;

import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.block.FinalAceBlockItem;
import silly.chemthunder.redemption.item.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface RedemptionItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    //  Item WEAPON_RACK = create("weapon_rack", new WeaponRackItem(new Item.Settings()));

    Item QUARTZ_KATANA = create("quartz_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0xc1c1d2, 0x535373)
            .hitSound(SoundEvents.BLOCK_NETHERRACK_BREAK)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item REDSTONE_KATANA = create("redstone_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0xe95050, 0x63374a)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item AMETHYST_KATANA = create("amethyst_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0xffffff, 0xc1c2c2)
            .hitSound(SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item COPPER_KATANA = create("copper_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0xea8770, 0x904931)
            .hitSound(SoundEvents.BLOCK_COPPER_BULB_BREAK)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item LAPIS_KATANA = create("lapis_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0xc9edf9, 0x8bcadd)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item NETHERITE_KATANA = create("netherite_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0x1a1a1a, 0x0e0e0e)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item EMERALD_KATANA = create("emerald_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0x20d64b, 0x096a31)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));

    Item SCULK_KATANA = create("sculk_katana", new KatanaItem(new AcornItemSettings()
            .sweepParticle(0x111b21, 0x034150)
            .hitSound(SoundEvents.BLOCK_SCULK_PLACE)
            .maxCount(1)
            .fireproof()
            .attributeModifiers(KatanaItem.createAttributeModifiers())
    ));


    Item QUARTZ_SHEATH = create("quartz_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item REDSTONE_SHEATH = create("redstone_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item EMERALD_SHEATH = create("emerald_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item COPPER_SHEATH = create("copper_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item NETHERITE_SHEATH = create("netherite_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item AMETHYST_SHEATH = create("amethyst_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item SCULK_SHEATH = create("sculk_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));

    Item LAPIS_SHEATH = create("lapis_sheath", new SheathItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathItem.createAttributeModifiers())
    ));


    // sheathed katanas
    Item AMETHYST_SHEATHED = create("amethyst_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item NETHERITE_SHEATHED = create("netherite_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item LAPIS_SHEATHED = create("lapis_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item REDSTONE_SHEATHED = create("redstone_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item EMERALD_SHEATHED = create("emerald_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item SCULK_SHEATHED = create("sculk_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item QUARTZ_SHEATHED = create("quartz_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));

    Item COPPER_SHEATHED = create("copper_sheathed", new SheathedKatanaItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SheathedKatanaItem.createAttributeModifiers())
    ));



    Item COURT_GLASS = create("court_glass", new CourtGlassItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item HUNTERS_GLASS = create("hunters_glass", new HuntersCourtGlassItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item SILENT_KEY = create("silent_key", new SilentKeyItem(new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(SilentKeyItem.createAttributeModifiers())
    ));

    Item FINAL_ACE_BLOCK_ITEM = create("final_ace", new FinalAceBlockItem(new AcornItemSettings()
            .maxCount(1)
    ));

    static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, Redemption.id(name));
        return item;
    }

    static void index() {
        ITEMS.forEach((item, id) -> Registry.register(Registries.ITEM, id, item));

        // colors
        modifyItemNameColor(QUARTZ_KATANA, 0xc1c1d2);
        modifyItemNameColor(QUARTZ_SHEATH, 0xc1c1d2);
        modifyItemNameColor(QUARTZ_SHEATHED, 0xc1c1d2);

        modifyItemNameColor(REDSTONE_KATANA, 0xe95050);
        modifyItemNameColor(REDSTONE_SHEATH, 0xe95050);
        modifyItemNameColor(REDSTONE_SHEATHED, 0xe95050);

        modifyItemNameColor(AMETHYST_KATANA, 0xe28aff);
        modifyItemNameColor(AMETHYST_SHEATH, 0xe28aff);
        modifyItemNameColor(AMETHYST_SHEATHED, 0xe28aff);

        modifyItemNameColor(SCULK_KATANA, 0x041716);
        modifyItemNameColor(SCULK_SHEATH, 0x041716);
        modifyItemNameColor(SCULK_SHEATHED, 0x041716);

        modifyItemNameColor(COPPER_KATANA, 0x904931);
        modifyItemNameColor(COPPER_SHEATH, 0x904931);
        modifyItemNameColor(COPPER_SHEATHED, 0x904931);

        modifyItemNameColor(LAPIS_KATANA, 0x8bcadd);
        modifyItemNameColor(LAPIS_SHEATH, 0x8bcadd);
        modifyItemNameColor(LAPIS_SHEATHED, 0x8bcadd);

        modifyItemNameColor(EMERALD_KATANA, 0x096a31);
        modifyItemNameColor(EMERALD_SHEATH, 0x096a31);
        modifyItemNameColor(EMERALD_SHEATHED, 0x096a31);

        modifyItemNameColor(NETHERITE_KATANA, 0xe4d971);
        modifyItemNameColor(NETHERITE_SHEATH, 0xe4d971);
        modifyItemNameColor(NETHERITE_SHEATHED, 0xe4d971);


        modifyItemNameColor(HUNTERS_GLASS, 0xb629eb);
        modifyItemNameColor(COURT_GLASS, 0x59ffff);
        modifyItemNameColor(Item.fromBlock(RedemptionBlocks.FINAL_ACE), 0xff0068);
        modifyItemNameColor(SILENT_KEY, 0xff0068);
    }

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Redemption.id(name), item);
    }
}
