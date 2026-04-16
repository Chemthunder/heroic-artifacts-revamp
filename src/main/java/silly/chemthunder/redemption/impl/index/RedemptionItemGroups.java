package silly.chemthunder.redemption.impl.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import silly.chemthunder.redemption.impl.Redemption;

public interface RedemptionItemGroups {
    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Redemption.id(Redemption.MOD_ID));
    ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(RedemptionItems.COURT_GLASS))
            .displayName(Text.translatable("itemGroup." + Redemption.MOD_ID))
            .build();

    static void init() {
        Registry.register(Registries.ITEM_GROUP, GROUP_KEY, ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(RedemptionItemGroups::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries itemGroup) {
        for (Item item : RedemptionItems.ITEMS.toRegister) {
            itemGroup.add(item);
        }
    }
}
