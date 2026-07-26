package net.the_hero_robot.redemption.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.index.RNItems;
import net.the_hero_robot.redemption.impl.index.tag.RNItemTags;
import net.the_hero_robot.redemption.impl.item.KatanaItem;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RNItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public RNItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(RNItemTags.KATANAS)
                .add(RNItems.ITEMS.toRegister.stream().filter(item -> item instanceof KatanaItem).toArray(Item[]::new))
                .setReplace(false);
    }
}
