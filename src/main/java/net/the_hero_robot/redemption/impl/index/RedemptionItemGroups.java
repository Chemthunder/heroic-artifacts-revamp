package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.ItemGroupRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.item.KatanaItem;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RedemptionItemGroups {
    ItemGroupRegistrant GROUPS = new ItemGroupRegistrant(Redemption.MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Redemption.id(Redemption.MOD_ID));
    ItemGroup MAIN = GROUPS.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(() -> new ItemStack(RedemptionItems.COURT_GLASS))
            .displayName(Text.translatable("itemGroup." + Redemption.MOD_ID))
            .build());

    static void init() {
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

        itemGroup.add(RedemptionItems.DRAGON_SWORD);
    }
}
