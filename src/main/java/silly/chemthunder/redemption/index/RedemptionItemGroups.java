package silly.chemthunder.redemption.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import silly.chemthunder.redemption.Redemption;

public interface RedemptionItemGroups {
    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Redemption.id("redemption"));
    ItemGroup A_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RedemptionItems.COURT_GLASS))
            .displayName(Text.translatable("itemgroup.redemption"))
            .build();

    static void index() {
        Registry.register(Registries.ITEM_GROUP, GROUP_KEY, A_GROUP);
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(RedemptionItemGroups::addEntries);

    }

    private static void addEntries(FabricItemGroupEntries itemGroup) {
        itemGroup.add(RedemptionItems.REDSTONE_KATANA);
        itemGroup.add(RedemptionItems.REDSTONE_SHEATH);
        itemGroup.add(RedemptionItems.QUARTZ_KATANA);
        itemGroup.add(RedemptionItems.QUARTZ_SHEATH);
        itemGroup.add(RedemptionItems.EMERALD_KATANA);
        itemGroup.add(RedemptionItems.EMERALD_SHEATH);
        itemGroup.add(RedemptionItems.AMETHYST_KATANA);
        itemGroup.add(RedemptionItems.AMETHYST_SHEATH);
        itemGroup.add(RedemptionItems.NETHERITE_KATANA);
        itemGroup.add(RedemptionItems.NETHERITE_SHEATH);
        itemGroup.add(RedemptionItems.COPPER_KATANA);
        itemGroup.add(RedemptionItems.COPPER_SHEATH);
        itemGroup.add(RedemptionItems.SCULK_KATANA);
        itemGroup.add(RedemptionItems.SCULK_SHEATH);
        itemGroup.add(RedemptionItems.LAPIS_KATANA);
        itemGroup.add(RedemptionItems.LAPIS_SHEATH);



        itemGroup.add(RedemptionItems.COURT_GLASS);
        itemGroup.add(RedemptionItems.HUNTERS_GLASS);
    }
}
