package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.index.RedemptionItems;
import net.the_hero_robot.redemption.impl.index.tag.RedemptionItemTags;
import net.the_hero_robot.redemption.impl.item.KatanaItem;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RedemptionItemTagGen extends FabricTagProvider.ItemTagProvider {
    public RedemptionItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(RedemptionItemTags.KATANAS)
                .add(RedemptionItems.ITEMS.toRegister.stream().filter(item -> item instanceof KatanaItem).toArray(Item[]::new))
                .setReplace(false);
    }

    public String getName() {
        return Redemption.MOD_ID + "_item_tags";
    }
}
