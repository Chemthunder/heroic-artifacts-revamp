package silly.chemthunder.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import silly.chemthunder.redemption.impl.index.RedemptionItems;
import silly.chemthunder.redemption.impl.index.tag.RedemptionItemTags;
import silly.chemthunder.redemption.impl.item.AshiroKatanaItem;
import silly.chemthunder.redemption.impl.item.AshiroSheathItem;
import silly.chemthunder.redemption.impl.item.SheathedAshiroKatanaItem;
import silly.chemthunder.redemption.impl.item.katana.KatanaItem;
import silly.chemthunder.redemption.impl.item.katana.SheathItem;
import silly.chemthunder.redemption.impl.item.katana.SheathedKatanaItem;

import java.util.concurrent.CompletableFuture;

public class RedemptionItemTagGen extends FabricTagProvider.ItemTagProvider {
    public RedemptionItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(RedemptionItemTags.KATANAS)
                .add(RedemptionItems.ITEMS.toRegister.stream().filter(item -> item instanceof KatanaItem || item instanceof AshiroKatanaItem).toArray(Item[]::new))
                .setReplace(false);

        this.getOrCreateTagBuilder(RedemptionItemTags.SHEATHS)
                .add(RedemptionItems.ITEMS.toRegister.stream().filter(item -> item instanceof SheathItem || item instanceof AshiroSheathItem).toArray(Item[]::new))
                .setReplace(false);

        this.getOrCreateTagBuilder(RedemptionItemTags.SHEATHED_KATANAS)
                .add(RedemptionItems.ITEMS.toRegister.stream().filter(item -> item instanceof SheathedKatanaItem || item instanceof SheathedAshiroKatanaItem).toArray(Item[]::new))
                .setReplace(false);
    }
}
