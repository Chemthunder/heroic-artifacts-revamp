package net.the_hero_robot.arcane_kings.impl.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 */
public interface AKItemGroups {
    CreativeModeTabRegistrant GROUPS = new CreativeModeTabRegistrant(Redemption.MOD_ID);

    ItemGroup KINGS = GROUPS.register("arcane_kings", FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.arcane_kings"))
            .icon(AKItems.ARCHMAGE_BLADE::getDefaultStack)
            .entries(AKItemGroups::buildEntries)
            .build());

    static void init() {}

    private static void buildEntries(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        entries.add(AKItems.ARCHMAGE_BLADE);
    }
}
