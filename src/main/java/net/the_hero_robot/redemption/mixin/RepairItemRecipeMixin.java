package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.the_hero_robot.redemption.impl.item.KatanaItem;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin {
    @WrapMethod(method = "canCombineStacks")
    private static boolean redemption$disallowCombiningKatanas(ItemStack first, ItemStack second, Operation<Boolean> original) {
        return original.call(first, second) && !(first.getItem() instanceof KatanaItem) && !(second.getItem() instanceof KatanaItem);
    }
}
