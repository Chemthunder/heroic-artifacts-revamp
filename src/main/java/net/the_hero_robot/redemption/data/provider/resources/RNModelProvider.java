package net.the_hero_robot.redemption.data.provider.resources;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RNItems;
import net.the_hero_robot.redemption.impl.index.client.RNModels;
import net.the_hero_robot.redemption.impl.item.KatanaItem;
import net.the_hero_robot.redemption.impl.util.RNUtil;

/**
 * @author AcoYT
 */
public class RNModelProvider extends FabricModelProvider {
    public RNModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {}

    public void generateItemModels(ItemModelGenerator generator) {
        for (Item item : RNItems.ITEMS.toRegister) {
            if (item instanceof KatanaItem) {
                registerKatana(generator, item);
            }
        }

        generator.register(RNItems.COURT_GLASS, Models.GENERATED);
        generator.register(RNItems.HUNTERS_GLASS, Models.GENERATED);
        generator.register(RNItems.ICE_CREAM, RNModels.HANDHELD_FLIPPED);

        generator.register(RNItems.DRAGON_SWORD, Models.GENERATED);
    }

    private static void registerKatana(ItemModelGenerator generator, Item item) {
        for (KatanaComponent.BladeType bladeType : KatanaComponent.BladeType.values()) {
            Identifier id = RNUtil.formatKatanaId(item, bladeType, KatanaComponent.get(item.getDefaultStack()).type(), true);
            Identifier inHandId = id.withSuffixedPath("_in_hand");
            Models.GENERATED.upload(id, TextureMap.layer0(id), generator.writer);

            Model inHandModel = bladeType == KatanaComponent.BladeType.KATANA
                    ? RNModels.KATANA_IN_HAND
                    : bladeType == KatanaComponent.BladeType.SHEATH
                    ? RNModels.SHEATH_IN_HAND
                    : RNModels.SHEATHED_KATANA_IN_HAND;

            inHandModel.upload(inHandId, TextureMap.layer0(inHandId), generator.writer);
        }
    }
}
