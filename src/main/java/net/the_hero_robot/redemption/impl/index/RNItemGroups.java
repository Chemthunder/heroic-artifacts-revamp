package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
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
public interface RNItemGroups {
    CreativeModeTabRegistrant GROUPS = new CreativeModeTabRegistrant(Redemption.MOD_ID);

    RegistryKey<ItemGroup> MAIN_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Redemption.id(Redemption.MOD_ID));
    ItemGroup MAIN = GROUPS.register(MAIN_KEY.getValue().getPath(), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + Redemption.MOD_ID))
            .icon(() -> new ItemStack(RNItems.COURT_GLASS))
            .entries(RNItemGroups::addMainEntries)
            .build());

    static void init() {}

    private static void addMainEntries(ItemGroup.DisplayContext context, ItemGroup.Entries entries) {
        RNItems.ITEMS.toRegister.forEach(item -> {
            if (item instanceof KatanaItem) {
                for (KatanaComponent.BladeType bladeType : KatanaComponent.BladeType.values()) {
                    ItemStack stack = item.getDefaultStack();
                    KatanaComponent component = KatanaComponent.get(stack);

                    stack.set(RNDataComponents.KATANA, component.withBladeType(bladeType));
                    stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, KatanaItem.createAttributeModifiers(bladeType));

                    entries.add(stack);
                }
            }
        });
    }
}
