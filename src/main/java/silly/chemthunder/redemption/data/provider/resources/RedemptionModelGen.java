package silly.chemthunder.redemption.data.provider.resources;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import silly.chemthunder.redemption.impl.index.RedemptionItems;
import silly.chemthunder.redemption.impl.index.client.RedemptionModels;
import silly.chemthunder.redemption.impl.item.katana.KatanaItem;
import silly.chemthunder.redemption.impl.item.katana.SheathItem;
import silly.chemthunder.redemption.impl.item.katana.SheathedKatanaItem;

public class RedemptionModelGen extends FabricModelProvider {
    public RedemptionModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {}

    public void generateItemModels(ItemModelGenerator generator) {
        for (Item item : RedemptionItems.ITEMS.toRegister) {
            if (item instanceof KatanaItem) {
                registerInHandVarying(generator, item, RedemptionModels.KATANA_IN_HAND);
            }

            if (item instanceof SheathItem) {
                registerInHandVarying(generator, item, RedemptionModels.SHEATH_IN_HAND);
            }

            if (item instanceof SheathedKatanaItem) {
                registerInHandVarying(generator, item, RedemptionModels.SHEATHED_IN_HAND);
            }
        }

        registerInHandVarying(generator, RedemptionItems.ASHIRO_KATANA, RedemptionModels.LARGE_KATANA_IN_HAND);
        registerInHandVarying(generator, RedemptionItems.ASHIRO_SHEATH, RedemptionModels.LARGE_SHEATH_IN_HAND);
        registerInHandVarying(generator, RedemptionItems.ASHIRO_SHEATHED, RedemptionModels.SHEATHED_IN_HAND);


        generator.register(RedemptionItems.COURT_GLASS, Models.GENERATED);
        generator.register(RedemptionItems.HUNTERS_GLASS, Models.GENERATED);
    }

    private static void registerInHandVarying(ItemModelGenerator generator, Item item, Model inHandModel) {
        Identifier id = Registries.ITEM.getId(item).withPrefixedPath("item/");
        Identifier inHandId = id.withSuffixedPath("_in_hand");

        Models.GENERATED.upload(id, TextureMap.layer0(id), generator.writer);
        inHandModel.upload(inHandId, TextureMap.layer0(inHandId), generator.writer);
    }
}
