package silly.chemthunder.redemption.impl.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.component.KatanaComponent;
import silly.chemthunder.redemption.impl.item.KatanaItem;

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
        RedemptionItems.ITEMS.toRegister.forEach(item -> {
            if (item instanceof KatanaItem) {
                for (KatanaComponent.BladeType bladeType : KatanaComponent.BladeType.values()) {
                    ItemStack stack = item.getDefaultStack();
                    KatanaComponent component = KatanaComponent.get(stack);

                    stack.set(RedemptionDataComponents.KATANA, component.withBladeType(bladeType));
                    stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, KatanaItem.createAttributeModifiers(bladeType));

                    itemGroup.add(stack);
                }
            } else {
                itemGroup.add(item);
            }
        });
    }
}
