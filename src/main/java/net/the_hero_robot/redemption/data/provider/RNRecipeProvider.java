package net.the_hero_robot.redemption.data.provider;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RNDataComponents;
import net.the_hero_robot.redemption.impl.index.RNItems;
import net.the_hero_robot.redemption.impl.item.KatanaItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class RNRecipeProvider extends FabricRecipeProvider {
    public RNRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void generate(RecipeExporter exporter) {
        Map<ItemConvertible, ItemConvertible> material = new Object2ObjectOpenHashMap<>();
        for (Item item : Registries.ITEM) {
            if (item instanceof KatanaItem) {
                ItemStack stack = item.getDefaultStack();
                if (stack.contains(RNDataComponents.KATANA)) {
                    Ingredient ingredient = KatanaComponent.get(stack).material();
                    if (getFromIngredient(ingredient).isEmpty()) continue;
                    for (ItemConvertible convertible : getFromIngredient(ingredient)) {
                        material.put(convertible, item);
                    }
                }
            }
        }

        material.forEach((ingredient, katana) -> {
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, katana)
                    .pattern("N")
                    .pattern("I")
                    .pattern("S")
                    .input('N', Items.NETHERITE_INGOT)
                    .input('I', ingredient)
                    .input('S', Items.STICK)
                    .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                    .offerTo(exporter, Registries.ITEM.getId(katana.asItem()).withSuffixedPath("_from_" + Registries.ITEM.getId(ingredient.asItem()).getPath()));
        });

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, RNItems.ICE_CREAM)
                .input(Ingredient.fromTag(ConventionalItemTags.MILK_DRINKS))
                .input(Ingredient.ofItems(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE))
                .input(Items.WHEAT)
                .criterion(hasItem(Items.MILK_BUCKET), conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter);
    }

    public List<ItemConvertible> getFromIngredient(Ingredient ingredient) {
        if (ingredient.isEmpty()) return new ArrayList<>();
        List<ItemConvertible> convertibles = new ArrayList<>();
        for (Item item : Registries.ITEM) {
            if (ingredient.test(item.getDefaultStack())) {
                convertibles.add(item);
            }
        }

        return convertibles;
    }
}
