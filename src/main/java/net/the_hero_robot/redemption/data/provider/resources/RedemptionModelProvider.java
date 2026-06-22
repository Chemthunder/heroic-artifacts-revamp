package net.the_hero_robot.redemption.data.provider.resources;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RedemptionItems;
import net.the_hero_robot.redemption.impl.index.client.RedemptionModels;
import net.the_hero_robot.redemption.impl.item.KatanaItem;
import net.the_hero_robot.redemption.impl.util.ModUtil;

/**
 * @author AcoYT
 */
public class RedemptionModelProvider extends FabricModelProvider {
    public RedemptionModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {}

    public void generateItemModels(ItemModelGenerator generator) {
        for (Item item : RedemptionItems.ITEMS.toRegister) {
            if (item instanceof KatanaItem) {
                registerKatana(generator, item);
            }
        }

        generator.register(RedemptionItems.COURT_GLASS, Models.GENERATED);
        generator.register(RedemptionItems.HUNTERS_GLASS, Models.GENERATED);
        generator.register(RedemptionItems.ICE_CREAM, RedemptionModels.HANDHELD_FLIPPED);

        generator.register(RedemptionItems.DRAGON_SWORD, Models.GENERATED);
    }

    private static void registerKatana(ItemModelGenerator generator, Item item) {
        for (KatanaComponent.BladeType bladeType : KatanaComponent.BladeType.values()) {
            Identifier id = ModUtil.formatKatanaId(item, bladeType, KatanaComponent.get(item.getDefaultStack()).type(), true);
            Identifier inHandId = id.withSuffixedPath("_in_hand");
            Models.GENERATED.upload(id, TextureMap.layer0(id), generator.writer);

            Model inHandModel = bladeType == KatanaComponent.BladeType.KATANA
                    ? RedemptionModels.KATANA_IN_HAND
                    : bladeType == KatanaComponent.BladeType.SHEATH
                    ? RedemptionModels.SHEATH_IN_HAND
                    : RedemptionModels.SHEATHED_KATANA_IN_HAND;

            inHandModel.upload(inHandId, TextureMap.layer0(inHandId), generator.writer);
        }
    }
}
