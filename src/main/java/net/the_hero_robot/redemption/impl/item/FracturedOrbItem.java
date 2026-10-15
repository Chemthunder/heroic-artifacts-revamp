package net.the_hero_robot.redemption.impl.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.cca.entity.FracturedComponent;

import java.util.List;

/**
 * @author AcoYT
 */
public class FracturedOrbItem extends Item {
    public FracturedOrbItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, user.getBoundingBox().expand(5), p -> true);
            if (!players.isEmpty()) {
                for (PlayerEntity player : players) {
                    FracturedComponent.KEY.get(player).setMaximum(200);
                }

                user.getItemCooldownManager().set(this, 200);
                return TypedActionResult.success(stack, world.isClient);
            } else {
                return TypedActionResult.fail(stack);
            }
        }

        return super.use(world, user, hand);
    }
}
