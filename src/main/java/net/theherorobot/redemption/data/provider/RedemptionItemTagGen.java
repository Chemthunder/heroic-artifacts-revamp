package net.theherorobot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.theherorobot.redemption.impl.index.RedemptionItems;
import net.theherorobot.redemption.impl.index.tag.RedemptionItemTags;
import net.theherorobot.redemption.impl.item.KatanaItem;

import java.util.concurrent.CompletableFuture;

public class RedemptionItemTagGen extends FabricTagProvider.ItemTagProvider {
    public RedemptionItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(RedemptionItemTags.KATANAS)
                .add(RedemptionItems.ITEMS.toRegister.stream().filter(item -> item instanceof KatanaItem).toArray(Item[]::new))
                .setReplace(false);
    }
}
